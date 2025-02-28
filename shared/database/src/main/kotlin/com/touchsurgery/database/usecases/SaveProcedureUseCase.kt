package com.touchsurgery.database.usecases

import androidx.room.withTransaction
import com.touchsurgery.database.DigitalSurgeryDatabase
import com.touchsurgery.database.entities.ProcedurePhaseCrossRefEntity
import com.touchsurgery.database.entities.ProcedureWithDetails

class SaveProcedureUseCase(
    private val db: DigitalSurgeryDatabase,
) {

    suspend operator fun invoke(procedure: ProcedureWithDetails) {
        val iconEntities = mutableSetOf(procedure.icon)
        procedure.phases.mapTo(iconEntities) { it.icon }
        val phaseEntities = procedure.phases.map { it.phase }
        val crossRefEntities = procedure.phases.map { phase ->
            ProcedurePhaseCrossRefEntity(
                procedureId = procedure.procedure.procedureId,
                phaseId = phase.phase.phaseId,
            )
        }
        db.withTransaction {
            db.getIconDao().insert(*iconEntities.toTypedArray())
            db.getPhaseDao().insert(*phaseEntities.toTypedArray())
            db.getProcedureDao().insert(procedure.procedure)
            db.getProcedureDao().insert(*crossRefEntities.toTypedArray())
        }
    }
}
