package com.touchsurgery.app.main.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.touchsurgery.app.R
import com.touchsurgery.app.main.model.MainAction
import com.touchsurgery.procedures.models.IconModel
import com.touchsurgery.procedures.models.ProcedureDetails
import com.touchsurgery.procedures.models.ProcedurePreview
import kotlin.random.Random

@Composable
fun ProcedureCompactCard(
    modifier: Modifier = Modifier,
    procedure: ProcedurePreview,
    action: (MainAction) -> Unit,
) = ProcedureCompactCard(
    modifier = modifier,
    onItemClick = { action(MainAction.OnProcedureClick(procedure.id)) },
    onFavoriteChange = { action(MainAction.OnPreviewFavoriteToggle(procedure)) },
    name = procedure.name,
    icon = procedure.icon,
    phaseCount = procedure.phases.size,
    isFavorite = procedure.isFavorite,
)

@Composable
fun ProcedureCompactCard(
    modifier: Modifier = Modifier,
    procedure: ProcedureDetails,
    action: (MainAction) -> Unit,
) = ProcedureCompactCard(
    modifier = modifier,
    onItemClick = { action(MainAction.OnProcedureClick(procedure.id)) },
    onFavoriteChange = { action(MainAction.OnDetailsFavoriteToggle(procedure)) },
    name = procedure.name,
    icon = procedure.icon,
    phaseCount = procedure.phases.size,
    isFavorite = procedure.isFavorite,
)

@Composable
fun ProcedureCompactCard(
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit,
    onFavoriteChange: (Boolean) -> Unit,
    name: String,
    icon: IconModel,
    phaseCount: Int,
    isFavorite: Boolean,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        onClick = onItemClick,
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {

            Box(
                modifier = Modifier
                    .defaultMinSize(minWidth = 80.dp)
                    .aspectRatio(1f),
            ) {
                if (LocalView.current.isInEditMode) {
                    Image(
                        modifier = Modifier.matchParentSize(),
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                    )
                } else {
                    AsyncImage(
                        modifier = Modifier.matchParentSize(),
                        model = icon.url,
                        contentDescription = name,
                    )
                }
            }
            Row(
                modifier = Modifier.padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.titleSmall,
                    )
                    Text(
                        text = stringResource(R.string.title_phase_count, phaseCount),
                        style = MaterialTheme.typography.labelSmall,
                    )
                }
                FavoriteIconSwitch(
                    checked = isFavorite,
                    onCheckedChange = onFavoriteChange,
                )
            }
        }

    }
}

@Preview
@Composable
private fun ProcedureCompactCardPreview() {
    MaterialTheme {
        Box {
            ProcedureCompactCard(
                modifier = Modifier
                    .width(180.dp)
                    .wrapContentHeight(),
                name = "Procedure 1",
                phaseCount = 3,
                icon = IconModel("", "", 1),
                isFavorite = Random.nextBoolean(),
                onItemClick = {},
                onFavoriteChange = {},
            )
        }
    }
}
