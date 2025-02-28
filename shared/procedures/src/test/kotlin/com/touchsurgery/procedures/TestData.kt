package com.touchsurgery.procedures

import com.touchsurgery.database.entities.IconEntity
import com.touchsurgery.database.entities.PhaseEntity
import com.touchsurgery.database.entities.PhaseWithDetails
import com.touchsurgery.database.entities.ProcedureEntity
import com.touchsurgery.database.entities.ProcedureWithDetails
import com.touchsurgery.networking.models.IconResponse
import com.touchsurgery.networking.models.ProcedureDetailsResponse
import com.touchsurgery.networking.models.ProcedurePreviewResponse
import kotlinx.datetime.Instant
import kotlin.random.Random

object TestData {

    val iconResponse1 = IconResponse(
        uuid = "uuid1",
        url = "url1",
        version = 1,
    )

    val iconResponse2 = IconResponse(
        uuid = "uuid2",
        url = "url2",
        version = 1,
    )

    val iconResponse3 = IconResponse(
        uuid = "uuid3",
        url = "url3",
        version = 1,
    )

    val procedurePreviewResponse1 = ProcedurePreviewResponse(
        uuid = "uuid1",
        name = "Procedure 1",
        icon = iconResponse1,
        phases = listOf("phase1", "phase2"),
        datePublished = Instant.DISTANT_FUTURE,
        duration = Random.nextInt(),
    )

    val procedurePreviewResponse2 = ProcedurePreviewResponse(
        uuid = "uuid2",
        name = "Procedure 2",
        icon = iconResponse2,
        phases = listOf("phase3", "phase4"),
        datePublished = Instant.DISTANT_FUTURE,
        duration = Random.nextInt(),
    )

    val procedureDetailsResponse1 = ProcedureDetailsResponse(
        uuid = "uuid1",
        name = "Procedure 1",
        icon = iconResponse1,
        phases = listOf(
            ProcedureDetailsResponse.Phase(
                uuid = "uuid1",
                name = "phase1",
                icon = iconResponse2,
            ),
            ProcedureDetailsResponse.Phase(
                uuid = "uuid2",
                name = "phase2",
                icon = iconResponse3,
            ),
        ),
        datePublished = Instant.DISTANT_FUTURE,
        duration = Random.nextInt(),
    )

    val procedureWithDetails1 = procedureDetailsResponse1.let { procedure ->
        ProcedureWithDetails(
            procedure = ProcedureEntity(
                procedureId = procedure.uuid,
                name = procedure.name,
                iconId = procedure.icon.uuid,
                datePublished = procedure.datePublished,
                duration = procedure.duration,
            ),
            icon = IconEntity(procedure.icon.uuid, procedure.icon.url, procedure.icon.version),
            phases = procedure.phases.map { phase ->
                PhaseWithDetails(
                    phase = PhaseEntity(phase.uuid, phase.name, phase.icon.uuid),
                    icon = IconEntity(phase.icon.uuid, phase.icon.url, phase.icon.version),
                )
            },
        )
    }
}
