package com.touchsurgery.procedures.usecases

import com.touchsurgery.database.usecases.GetProcedureIdsUseCase
import com.touchsurgery.networking.api.DigitalSurgeryService
import com.touchsurgery.procedures.models.ProcedureDetails

class FetchProcedureDetailsUseCase(
    private val service: DigitalSurgeryService,
    private val getProcedureIdsUseCase: GetProcedureIdsUseCase,
) {

    suspend operator fun invoke(procedureId: String): ProcedureDetails {
        val response = service.getProcedureDetails(procedureId)

        return ProcedureDetails.fromResponse(
            response,
            isFavorite = getProcedureIdsUseCase().contains(procedureId),
        )
    }
}
