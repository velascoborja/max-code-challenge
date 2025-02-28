package com.touchsurgery.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "procedure_phase_cross_ref",
    primaryKeys = ["procedureId", "phaseId"],
    foreignKeys = [
        ForeignKey(
            entity = ProcedureEntity::class,
            parentColumns = ["procedureId"],
            childColumns = ["procedureId"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = PhaseEntity::class,
            parentColumns = ["phaseId"],
            childColumns = ["phaseId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("procedureId"), Index("phaseId")],
)
data class ProcedurePhaseCrossRefEntity(
    val procedureId: String,
    val phaseId: String,
)
