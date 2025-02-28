package com.touchsurgery.procedures.usecases

import com.touchsurgery.database.usecases.GetProcedureIdsUseCase
import com.touchsurgery.networking.api.DigitalSurgeryService
import com.touchsurgery.procedures.models.ProcedurePreview

class FetchProcedurePreviewsUseCase(
    private val service: DigitalSurgeryService,
    private val getProcedureIdsUseCase: GetProcedureIdsUseCase,
) {

    suspend operator fun invoke(): List<ProcedurePreview> {
        val response = service.getProcedures()
        val storedProcedureIds = getProcedureIdsUseCase()
        return response.map { previewResponse ->
            ProcedurePreview.fromResponse(
                previewResponse,
                isFavorite = storedProcedureIds.contains(previewResponse.uuid),
            )
        }
    }
}
