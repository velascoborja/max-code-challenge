package com.touchsurgery.procedures.usecases

import com.touchsurgery.database.usecases.ObserveProceduresUseCase
import com.touchsurgery.procedures.models.ProcedureDetails
import kotlinx.coroutines.flow.map

class ObserveFavoriteProceduresUseCase(
    private val observeProceduresUseCase: ObserveProceduresUseCase,
) {

    operator fun invoke() = observeProceduresUseCase().map { favorites ->
        favorites.map { procedure ->
            ProcedureDetails.fromEntity(procedure, true)
        }
    }
}
