package com.touchsurgery.app.main.model

import com.touchsurgery.app.main.model.events.ErrorMessageEvent
import com.touchsurgery.app.main.model.events.FavoriteToggledEvent
import com.touchsurgery.procedures.models.ProcedureDetails
import com.touchsurgery.procedures.models.ProcedurePreview

data class MainViewState(
    val route: MainNavigation = MainNavigation.PROCEDURES,

    val isLoading: Boolean = false,

    val procedures: List<ProcedurePreview> = emptyList(),
    val favorites: List<ProcedureDetails> = emptyList(),

    val procedureDetails: ProcedureDetails? = null,

    val favoriteToggledEvent: FavoriteToggledEvent? = null,
    val errorMessageEvent: ErrorMessageEvent? = null,
)
