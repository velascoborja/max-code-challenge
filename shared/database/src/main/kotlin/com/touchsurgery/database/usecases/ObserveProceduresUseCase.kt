package com.touchsurgery.database.usecases

import com.touchsurgery.database.DigitalSurgeryDatabase
import com.touchsurgery.database.entities.ProcedureWithDetails
import kotlinx.coroutines.flow.Flow

class ObserveProceduresUseCase(
    private val db: DigitalSurgeryDatabase,
) {

    operator fun invoke(): Flow<List<ProcedureWithDetails>> {
        return db.getProcedureDao().observeAllProceduresWithDetails()
    }
}
