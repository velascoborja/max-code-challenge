package com.touchsurgery.database.usecases

import com.touchsurgery.database.DigitalSurgeryDatabase

class RemoveProcedureUseCase(
    private val db: DigitalSurgeryDatabase,
) {

    suspend operator fun invoke(procedureId: String) {
        db.getProcedureDao().deleteById(procedureId)

        val orphanedPhaseIds = db.getPhaseDao().getOrphanedPhaseIds().map { it.phaseId }
        db.getPhaseDao().deletePhasesByIds(orphanedPhaseIds)

        val orphanedIconIds = db.getIconDao().getOrphanedIconIds().map { it.iconId }
        db.getIconDao().deleteIconsByIds(orphanedIconIds)
    }
}
