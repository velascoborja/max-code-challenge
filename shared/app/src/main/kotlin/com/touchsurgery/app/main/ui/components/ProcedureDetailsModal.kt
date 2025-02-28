package com.touchsurgery.app.main.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.touchsurgery.app.R
import com.touchsurgery.app.main.model.MainAction
import com.touchsurgery.app.main.model.events.ErrorMessageEvent
import com.touchsurgery.app.main.model.events.FavoriteToggledEvent
import com.touchsurgery.procedures.models.IconModel
import com.touchsurgery.procedures.models.ProcedureDetails
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProcedureDetailsModal(
    modifier: Modifier = Modifier,
    procedure: ProcedureDetails,
    favoriteToggledEvent: FavoriteToggledEvent?,
    errorMessageEvent: ErrorMessageEvent?,
    action: (MainAction) -> Unit,
) {
    val modalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val snackbarHostState = remember { SnackbarHostState() }

    SnackbarFavoriteToggledEventEffect(
        snackbarHostState = snackbarHostState,
        favoriteToggledEvent = favoriteToggledEvent,
        action = action,
    )

    SnackbarErrorMessageEventEffect(
        snackbarHostState = snackbarHostState,
        errorMessageEvent = errorMessageEvent,
        action = action,
    )

    ModalBottomSheet(
        modifier = modifier.safeDrawingPadding(),
        sheetState = modalBottomSheetState,
        onDismissRequest = { action(MainAction.OnProcedureClose(procedure)) },
    ) {
        Box {
            ProcedureDetailsModalContent(
                procedure = procedure,
                action = action,
            )
            SnackbarHost(
                modifier = Modifier
                    .safeDrawingPadding()
                    .align(Alignment.BottomCenter),
                hostState = snackbarHostState,
            )
        }
    }
}

@Composable
fun ProcedureDetailsModalContent(
    modifier: Modifier = Modifier,
    procedure: ProcedureDetails,
    action: (MainAction) -> Unit,
) {

    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp),
            model = procedure.icon.url,
            contentDescription = procedure.name,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = procedure.name,
                style = MaterialTheme.typography.titleLarge,
            )
            FavoriteIconSwitch(
                checked = procedure.isFavorite,
                onCheckedChange = {
                    action(MainAction.OnDetailsFavoriteToggle(procedure))
                },
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = stringResource(R.string.title_total_duration_in_seconds, procedure.duration),
            style = MaterialTheme.typography.titleMedium,
        )

        val date = procedure.datePublished.toLocalDateTime(TimeZone.currentSystemDefault())
            .format(LocalDateTime.Format { byUnicodePattern("dd/MM/yyyy") })

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = stringResource(R.string.title_date_published, date),
            style = MaterialTheme.typography.titleMedium,
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(procedure.phases) { item ->
                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .width(180.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .width(180.dp)
                            .height(180.dp),
                        model = item.icon.url,
                        contentDescription = procedure.name,
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = item.name,
                        style = MaterialTheme.typography.titleSmall,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ProcedureDetailsModalContentPreview() {
    MaterialTheme {
        val icon = IconModel("", "", 1)
        ProcedureDetailsModalContent(
            procedure = ProcedureDetails(
                id = "",
                name = "Procedure 1",
                icon = icon,
                datePublished = Clock.System.now(),
                duration = Random.nextInt(120),
                isFavorite = Random.nextBoolean(),
                phases = List(5) { ProcedureDetails.Phase("", "Phase $it", icon) },
            ),
            action = {},
        )
    }
}
