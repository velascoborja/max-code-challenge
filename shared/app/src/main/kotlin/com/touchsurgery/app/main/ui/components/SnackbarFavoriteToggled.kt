package com.touchsurgery.app.main.ui.components

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import com.touchsurgery.app.R
import com.touchsurgery.app.main.model.MainAction
import com.touchsurgery.app.main.model.events.FavoriteToggledEvent
import kotlinx.coroutines.launch

@Composable
fun SnackbarFavoriteToggledEventEffect(
    snackbarHostState: SnackbarHostState,
    favoriteToggledEvent: FavoriteToggledEvent?,
    action: (MainAction) -> Unit,
) {
    val scope = rememberCoroutineScope()

    val snackbarText = when {
        favoriteToggledEvent == null -> null
        favoriteToggledEvent.isFavorite -> stringResource(
            R.string.title_added_to_favorites,
            favoriteToggledEvent.procedureName,
        )

        else -> stringResource(
            R.string.title_removed_from_favorites,
            favoriteToggledEvent.procedureName
        )
    }
    LaunchedEffect(snackbarText) {
        if (snackbarText != null) {
            scope.launch {
                snackbarHostState.currentSnackbarData?.dismiss()
                snackbarHostState.showSnackbar(
                    message = snackbarText,
                    withDismissAction = true,
                    duration = SnackbarDuration.Short,
                )
                action(MainAction.OnFavoriteEventConsumed)
            }
        }
    }
}
