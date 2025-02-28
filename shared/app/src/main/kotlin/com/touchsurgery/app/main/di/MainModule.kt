package com.touchsurgery.app.main.di

import com.touchsurgery.app.main.viewmodel.MainViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val moduleMain = module {

    viewModel<MainViewModel> {
        MainViewModel(
            fetchProcedureDetailsUseCase = get(),
            fetchProcedurePreviewsUseCase = get(),
            toggleFavoriteProcedureUseCase = get(),
            observeFavoriteProceduresUseCase = get(),
        )
    }
}
