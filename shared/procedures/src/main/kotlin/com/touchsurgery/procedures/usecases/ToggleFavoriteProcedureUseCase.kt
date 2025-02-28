package com.touchsurgery.procedures.usecases

import com.touchsurgery.database.entities.PhaseWithDetails
import com.touchsurgery.database.entities.ProcedureWithDetails
import com.touchsurgery.database.usecases.RemoveProcedureUseCase
import com.touchsurgery.database.usecases.SaveProcedureUseCase
import com.touchsurgery.procedures.models.IconModel.Companion.toEntity
import com.touchsurgery.procedures.models.ProcedureDetails
import com.touchsurgery.procedures.models.ProcedureDetails.Companion.toEntity
import com.touchsurgery.procedures.models.ProcedurePreview

class ToggleFavoriteProcedureUseCase(
    private val fetchProcedureDetailsUseCase: FetchProcedureDetailsUseCase,
    private val removeProcedureUseCase: RemoveProcedureUseCase,
    private val saveProcedureUseCase: SaveProcedureUseCase,
) {

    suspend operator fun invoke(procedure: ProcedureDetails) {
        if (procedure.isFavorite) {
            removeProcedureUseCase(procedure.id)
        } else {
            saveProcedure(procedure)
        }
    }

    suspend operator fun invoke(procedure: ProcedurePreview) {
        if (procedure.isFavorite) {
            removeProcedureUseCase(procedure.id)
        } else {
            saveProcedure(fetchProcedureDetailsUseCase(procedure.id))
        }
    }

    private suspend fun ToggleFavoriteProcedureUseCase.saveProcedure(
        procedure: ProcedureDetails
    ) {
        saveProcedureUseCase(
            ProcedureWithDetails(
                procedure = procedure.toEntity(),
                icon = procedure.icon.toEntity(),
                phases = procedure.phases.map {
                    PhaseWithDetails(
                        phase = it.toEntity(),
                        icon = it.icon.toEntity(),
                    )
                },
            )
        )
    }
}
