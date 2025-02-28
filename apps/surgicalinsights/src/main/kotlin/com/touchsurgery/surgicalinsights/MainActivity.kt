package com.touchsurgery.surgicalinsights

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
import com.touchsurgery.app.main.model.MainAction
import com.touchsurgery.app.main.model.MainViewState
import com.touchsurgery.app.main.ui.MainScreen
import com.touchsurgery.app.main.ui.components.ProcedureCompactCard
import com.touchsurgery.app.main.viewmodel.MainViewModel
import com.touchsurgery.surgicalinsights.ui.theme.SurgicalInsightsTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SurgicalInsightsTheme {
                val viewState by viewModel.viewState.collectAsState()

                SurgicalInsightsMainScreen(
                    viewState = viewState,
                    action = { viewModel.onAction(it) },
                )
            }
        }
    }
}

@Composable
fun SurgicalInsightsMainScreen(
    viewState: MainViewState,
    action: (MainAction) -> Unit,
) {
    MainScreen(
        viewState = viewState,
        proceduresContent = { procedures ->
            ProcedureGrid(
                procedures = procedures,
                itemContent = { item ->
                    ProcedureCompactCard(
                        modifier = Modifier,
                        procedure = item,
                        action = action,
                    )
                },
            )
        },
        favoritesContent = { procedures ->
            ProcedureGrid(
                procedures = procedures,
                itemContent = { item ->
                    ProcedureCompactCard(
                        procedure = item,
                        action = action,
                    )
                },
            )
        },
        action = action,
    )
}

@Composable
fun <T> ProcedureGrid(
    modifier: Modifier = Modifier,
    procedures: List<T>,
    itemContent: @Composable (T) -> Unit,
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

    val columnCount = when (windowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.MEDIUM -> 3
        WindowWidthSizeClass.EXPANDED -> 4
        WindowWidthSizeClass.COMPACT -> 2
        else -> 2
    }

    LazyVerticalStaggeredGrid(
        modifier = modifier.fillMaxWidth(),
        columns = StaggeredGridCells.Fixed(columnCount),
        verticalItemSpacing = 8.dp,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp),
    ) {
        items(procedures) { item ->
            itemContent(item)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SurgicalInsightsMainScreenPreview() {
    SurgicalInsightsTheme {
        SurgicalInsightsMainScreen(
            viewState = MainViewState(),
            action = {},
        )
    }
}
