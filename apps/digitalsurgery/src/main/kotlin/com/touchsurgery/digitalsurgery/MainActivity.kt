package com.touchsurgery.digitalsurgery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.touchsurgery.app.main.model.MainAction
import com.touchsurgery.app.main.model.MainViewState
import com.touchsurgery.app.main.ui.MainScreen
import com.touchsurgery.app.main.ui.components.ProcedureListCard
import com.touchsurgery.app.main.viewmodel.MainViewModel
import com.touchsurgery.digitalsurgery.ui.theme.DigitalSurgeryTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DigitalSurgeryTheme {

                val viewState by viewModel.viewState.collectAsState()

                DigitalSurgeryMainScreen(
                    viewState = viewState,
                    action = { viewModel.onAction(it) },
                )
            }
        }
    }
}

@Composable
fun DigitalSurgeryMainScreen(
    viewState: MainViewState,
    action: (MainAction) -> Unit,
) {
    MainScreen(
        viewState = viewState,
        proceduresContent = { procedures ->
            ProcedureList(
                procedures = procedures,
                itemContent = { item ->
                    ProcedureListCard(
                        procedure = item,
                        action = action,
                    )
                },
            )
        },
        favoritesContent = { procedures ->
            ProcedureList(
                procedures = procedures,
                itemContent = { item ->
                    ProcedureListCard(
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
fun <T> ProcedureList(
    modifier: Modifier = Modifier,
    procedures: List<T>,
    itemContent: @Composable (T) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp),
    ) {
        items(procedures) { item ->
            itemContent(item)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DigitalSurgeryMainScreenPreview() {
    DigitalSurgeryTheme {
        DigitalSurgeryMainScreen(
            viewState = MainViewState(),
            action = {},
        )
    }
}
