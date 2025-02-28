package com.touchsurgery.procedures.models

import com.touchsurgery.database.entities.PhaseEntity
import com.touchsurgery.database.entities.ProcedureEntity
import com.touchsurgery.database.entities.ProcedureWithDetails
import com.touchsurgery.networking.models.ProcedureDetailsResponse
import kotlinx.datetime.Instant

data class ProcedureDetails(
    val id: String,
    val name: String,
    val icon: IconModel,
    val phases: List<Phase>,
    val datePublished: Instant,
    val duration: Int,
    val isFavorite: Boolean,
) {

    data class Phase(
        val id: String,
        val name: String,
        val icon: IconModel,
    )

    companion object {

        fun fromResponse(
            response: ProcedureDetailsResponse,
            isFavorite: Boolean,
        ) = ProcedureDetails(
            id = response.uuid,
            name = response.name,
            icon = IconModel.fromResponse(response.icon),
            datePublished = response.datePublished,
            duration = response.duration,
            isFavorite = isFavorite,
            phases = response.phases.map { phase ->
                Phase(
                    id = phase.uuid,
                    name = phase.name,
                    icon = IconModel.fromResponse(phase.icon),
                )
            },
        )

        fun fromEntity(
            entity: ProcedureWithDetails,
            isFavorite: Boolean,
        ): ProcedureDetails = ProcedureDetails(
            id = entity.procedure.procedureId,
            name = entity.procedure.name,
            icon = IconModel.fromEntity(entity.icon),
            datePublished = entity.procedure.datePublished,
            duration = entity.procedure.duration,
            isFavorite = isFavorite,
            phases = entity.phases.map { phase ->
                Phase(
                    id = phase.phase.phaseId,
                    name = phase.phase.name,
                    icon = IconModel.fromEntity(phase.icon),
                )
            },
        )

        fun ProcedureDetails.toEntity() = ProcedureEntity(
            procedureId = id,
            name = name,
            iconId = icon.id,
            datePublished = datePublished,
            duration = duration,
        )

        fun Phase.toEntity() = PhaseEntity(
            phaseId = id,
            name = name,
            iconId = icon.id,
        )
    }
}
