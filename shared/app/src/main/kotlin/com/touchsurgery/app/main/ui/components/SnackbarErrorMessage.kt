package com.touchsurgery.app.main.ui.components

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import com.touchsurgery.app.R
import com.touchsurgery.app.main.model.MainAction
import com.touchsurgery.app.main.model.events.ErrorMessageEvent
import kotlinx.coroutines.launch

@Composable
fun SnackbarErrorMessageEventEffect(
    snackbarHostState: SnackbarHostState,
    errorMessageEvent: ErrorMessageEvent?,
    action: (MainAction) -> Unit,
) {
    val scope = rememberCoroutineScope()

    val defaultErrorMessage = stringResource(R.string.message_unknown_error_occurred)

    LaunchedEffect(errorMessageEvent) {
        if (errorMessageEvent != null) {
            scope.launch {
                snackbarHostState.currentSnackbarData?.dismiss()
                snackbarHostState.showSnackbar(
                    message = errorMessageEvent.errorMessage ?: defaultErrorMessage,
                    withDismissAction = true,
                    duration = SnackbarDuration.Short,
                )
                action(MainAction.OnErrorEventConsumed)
            }
        }
    }
}
