package com.touchsurgery.app.main.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.touchsurgery.app.R
import com.touchsurgery.app.main.model.MainAction
import com.touchsurgery.app.main.model.MainNavigation
import com.touchsurgery.app.main.model.MainViewState
import com.touchsurgery.app.main.ui.components.ProcedureDetailsModal
import com.touchsurgery.app.main.ui.components.SnackbarErrorMessageEventEffect
import com.touchsurgery.app.main.ui.components.SnackbarFavoriteToggledEventEffect
import com.touchsurgery.procedures.models.ProcedureDetails
import com.touchsurgery.procedures.models.ProcedurePreview

@Composable
fun MainScreen(
    viewState: MainViewState,
    proceduresContent: @Composable (List<ProcedurePreview>) -> Unit,
    favoritesContent: @Composable (List<ProcedureDetails>) -> Unit,
    action: (MainAction) -> Unit,
) {

    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            val parent = navBackStackEntry?.destination?.parent?.route

            NavigationBar(
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Bottom))
                    .height(48.dp),
            ) {
                MainNavigation.entries.forEach { item ->
                    val selected = currentRoute == item.name || parent == item.name
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            navController.navigate(item.name) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                modifier = Modifier,
                                painter = painterResource(item.iconResource),
                                tint = if (selected) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.secondary
                                },
                                contentDescription = null,
                            )
                        },
                    )
                }
            }
        },
    ) { paddingValues ->

        if (viewState.procedureDetails == null) {
            SnackbarFavoriteToggledEventEffect(
                snackbarHostState = snackbarHostState,
                favoriteToggledEvent = viewState.favoriteToggledEvent,
                action = action,
            )
            SnackbarErrorMessageEventEffect(
                snackbarHostState = snackbarHostState,
                errorMessageEvent = viewState.errorMessageEvent,
                action = action,
            )
        }

        Box(
            modifier = Modifier.padding(paddingValues),
        ) {
            NavHost(
                modifier = Modifier,
                navController = navController,
                startDestination = MainNavigation.PROCEDURES.name,
            ) {
                composable(MainNavigation.PROCEDURES.name) {
                    if (viewState.procedures.isEmpty() && viewState.isLoading.not()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Button(
                                onClick = { action(MainAction.OnProceduresRefresh) }
                            ) {
                                Text(
                                    text = stringResource(R.string.action_refresh).uppercase(),
                                    style = MaterialTheme.typography.labelMedium,
                                )
                            }
                        }
                    } else {
                        proceduresContent(viewState.procedures)
                    }
                }
                composable(MainNavigation.FAVORITES.name) {
                    favoritesContent(viewState.favorites)
                }
            }

            if (viewState.isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.secondary,
                )
            }

            viewState.procedureDetails?.let { procedure ->

                ProcedureDetailsModal(
                    modifier = Modifier,
                    procedure = procedure,
                    favoriteToggledEvent = viewState.favoriteToggledEvent,
                    errorMessageEvent = viewState.errorMessageEvent,
                    action = action,
                )
            }
        }
    }
}

@Preview(
    showSystemUi = true,
)
@Composable
private fun MainScreenPreview() {
    MaterialTheme {
        MainScreen(
            viewState = MainViewState(),
            proceduresContent = {},
            favoritesContent = {},
            action = {},
        )
    }
}
