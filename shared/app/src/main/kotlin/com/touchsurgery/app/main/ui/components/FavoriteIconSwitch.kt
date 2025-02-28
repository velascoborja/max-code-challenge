package com.touchsurgery.app.main.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.touchsurgery.app.R

@Composable
fun FavoriteIconSwitch(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    IconButton(
        modifier = modifier,
        onClick = { onCheckedChange(!checked) },
    ) {
        Icon(
            painter = painterResource(
                id = if (checked) {
                    R.drawable.ic_favorite_24
                } else {
                    R.drawable.ic_favorite_outline_24
                },
            ),
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = "Favorite",
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteIconSwitchPreview() {
    Column {
        var isFavorite by remember { mutableStateOf(false) }
        FavoriteIconSwitch(
            checked = isFavorite,
            onCheckedChange = { isFavorite = it },
        )
    }
}
