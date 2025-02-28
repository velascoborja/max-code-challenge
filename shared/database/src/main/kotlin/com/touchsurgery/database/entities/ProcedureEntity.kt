package com.touchsurgery.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import kotlinx.datetime.Instant

@Entity(
    tableName = "procedures",
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
data class ProcedureEntity(
    @PrimaryKey val procedureId: String,
    val name: String,
    val iconId: String,
    val datePublished: Instant,
    val duration: Int,
)

data class ProcedureWithDetails(
    @Embedded val procedure: ProcedureEntity,

    @Relation(
        parentColumn = "iconId",
        entityColumn = "iconId",
    )
    val icon: IconEntity,

    @Relation(
        entity = PhaseEntity::class,
        parentColumn = "procedureId",
        entityColumn = "phaseId",
        associateBy = Junction(ProcedurePhaseCrossRefEntity::class),
    )
    val phases: List<PhaseWithDetails>,
)
