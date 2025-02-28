package com.touchsurgery.database.entities

import androidx.room.DatabaseView
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(
    tableName = "phases",
    foreignKeys = [
        ForeignKey(
            entity = IconEntity::class,
            parentColumns = ["iconId"],
            childColumns = ["iconId"],
            onDelete = ForeignKey.SET_NULL,
        )
    ],
    indices = [Index("iconId")],
)
data class PhaseEntity(
    @PrimaryKey val phaseId: String,
    val name: String,
    val iconId: String,
)

data class PhaseWithDetails(
    @Embedded val phase: PhaseEntity,
    @Relation(
        parentColumn = "iconId",
        entityColumn = "iconId",
    )
    val icon: IconEntity,
)

@DatabaseView(
    viewName = "OrphanedPhaseIdView",
    value = """
        SELECT phases.phaseId
        FROM phases
        LEFT JOIN procedure_phase_cross_ref ON phases.phaseId = procedure_phase_cross_ref.phaseId
        WHERE procedure_phase_cross_ref.phaseId IS NULL
    """
)
data class OrphanedPhaseId(
    val phaseId: String,
)
