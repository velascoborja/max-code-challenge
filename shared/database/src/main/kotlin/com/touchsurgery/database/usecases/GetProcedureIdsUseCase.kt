package com.touchsurgery.database.usecases

import com.touchsurgery.database.DigitalSurgeryDatabase

class GetProcedureIdsUseCase(
    private val db: DigitalSurgeryDatabase,
) {

    suspend operator fun invoke(): List<String> {
        return db.getProcedureDao().getAllProcedureIds()
    }
}
