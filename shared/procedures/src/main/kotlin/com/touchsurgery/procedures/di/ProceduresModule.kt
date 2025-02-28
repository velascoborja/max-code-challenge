package com.touchsurgery.procedures.di

import com.touchsurgery.procedures.usecases.FetchProcedureDetailsUseCase
import com.touchsurgery.procedures.usecases.FetchProcedurePreviewsUseCase
import com.touchsurgery.procedures.usecases.ObserveFavoriteProceduresUseCase
import com.touchsurgery.procedures.usecases.ToggleFavoriteProcedureUseCase
import org.koin.dsl.module

val moduleProcedures = module {

    factory {
        FetchProcedurePreviewsUseCase(
            service = get(),
            getProcedureIdsUseCase = get(),
        )
    }

    factory {
        FetchProcedureDetailsUseCase(
            service = get(),
            getProcedureIdsUseCase = get(),
        )
    }

    factory {
        ObserveFavoriteProceduresUseCase(
            observeProceduresUseCase = get(),
        )
    }

    factory {
        ToggleFavoriteProcedureUseCase(
            fetchProcedureDetailsUseCase = get(),
            removeProcedureUseCase = get(),
            saveProcedureUseCase = get(),
        )
    }
}
