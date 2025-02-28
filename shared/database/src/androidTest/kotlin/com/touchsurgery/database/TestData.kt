package com.touchsurgery.database

import com.touchsurgery.database.entities.IconEntity
import com.touchsurgery.database.entities.PhaseEntity
import com.touchsurgery.database.entities.PhaseWithDetails
import com.touchsurgery.database.entities.ProcedureEntity
import com.touchsurgery.database.entities.ProcedurePhaseCrossRefEntity
import com.touchsurgery.database.entities.ProcedureWithDetails
import kotlinx.datetime.Instant

object TestData {
    val iconProcedure = IconEntity("icon1", "url1", 1)
    val iconPhase1 = IconEntity("icon2", "url2", 1)
    val iconPhase2 = IconEntity("icon3", "url3", 1)

    val phase1 = PhaseEntity(
        phaseId = "phase1",
        name = "Phase 1",
        iconId = iconPhase1.iconId,
    )
    val phase2 = PhaseEntity(
        phaseId = "phase2",
        name = "Phase 2",
        iconId = iconPhase2.iconId,
    )
    val procedure1 = ProcedureEntity(
        procedureId = "procedure1",
        name = "Procedure 1",
        iconId = iconProcedure.iconId,
        datePublished = Instant.DISTANT_FUTURE,
        duration = 30,
    )
    val crossRef1 = ProcedurePhaseCrossRefEntity(
        procedureId = procedure1.procedureId,
        phaseId = phase1.phaseId,
    )
    val crossRef2 = ProcedurePhaseCrossRefEntity(
        procedureId = procedure1.procedureId,
        phaseId = phase2.phaseId,
    )
    val procedureWithDetails = ProcedureWithDetails(
        procedure = procedure1,
        icon = iconProcedure,
        phases = listOf(
            PhaseWithDetails(phase1, iconPhase1),
            PhaseWithDetails(phase2, iconPhase2),
        ),
    )
}
