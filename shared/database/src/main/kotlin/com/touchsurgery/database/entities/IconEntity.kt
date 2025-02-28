package com.touchsurgery.database.entities

import androidx.room.DatabaseView
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "icons",
)
data class IconEntity(
    @PrimaryKey val iconId: String,
    val url: String,
    val version: Long,
)

@DatabaseView(
    viewName = "OrphanedIconIdView",
    value = """
        SELECT icons.iconId
        FROM icons
        LEFT JOIN phases ON icons.iconId = phases.iconId
        LEFT JOIN procedures ON icons.iconId = procedures.iconId
        WHERE phases.iconId IS NULL AND procedures.iconId IS NULL
    """
)
data class OrphanedIconId(
    val iconId: String,
)
