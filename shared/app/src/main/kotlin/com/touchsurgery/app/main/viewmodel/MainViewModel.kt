package com.touchsurgery.app.main.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.touchsurgery.app.main.model.MainAction
import com.touchsurgery.app.main.model.MainViewState
import com.touchsurgery.app.main.model.events.ErrorMessageEvent
import com.touchsurgery.app.main.model.events.FavoriteToggledEvent
import com.touchsurgery.procedures.usecases.FetchProcedureDetailsUseCase
import com.touchsurgery.procedures.usecases.FetchProcedurePreviewsUseCase
import com.touchsurgery.procedures.usecases.ObserveFavoriteProceduresUseCase
import com.touchsurgery.procedures.usecases.ToggleFavoriteProcedureUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val fetchProcedurePreviewsUseCase: FetchProcedurePreviewsUseCase,
    private val fetchProcedureDetailsUseCase: FetchProcedureDetailsUseCase,
    private val toggleFavoriteProcedureUseCase: ToggleFavoriteProcedureUseCase,
    private val observeFavoriteProceduresUseCase: ObserveFavoriteProceduresUseCase,
) : ViewModel() {

    private val _viewState = MutableStateFlow(MainViewState(isLoading = true))
    val viewState: StateFlow<MainViewState> = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            observeFavoriteProceduresUseCase().collect { favorites ->
                _viewState.update { state ->
                    state.copy(favorites = favorites)
                }
            }
        }
        fetchProcedures()
    }

    fun onAction(action: MainAction) {
        when (action) {
            is MainAction.OnProcedureClick -> {
                viewModelScope.launch {
                    val favoriteProcedureDetails = _viewState.value.favorites
                        .firstOrNull { it.id == action.procedureId }

                    if (favoriteProcedureDetails == null) {
                        _viewState.update { state ->
                            state.copy(isLoading = true)
                        }
                    }

                    try {
                        _viewState.update { state ->
                            val procedureDetails = favoriteProcedureDetails
                                ?: fetchProcedureDetailsUseCase(action.procedureId)

                            state.copy(
                                procedureDetails = procedureDetails,
                                isLoading = false,
                                favoriteToggledEvent = null,
                                errorMessageEvent = null,
                            )
                        }
                    } catch (e: Exception) {
                        Log.e("MainViewModel", e.message, e)
                        _viewState.update { state ->
                            state.copy(
                                isLoading = false,
                                errorMessageEvent = ErrorMessageEvent(e.message)
                            )
                        }
                    }
                }
            }

            is MainAction.OnProcedureClose -> {
                _viewState.update { state ->
                    state.copy(procedureDetails = null, favoriteToggledEvent = null)
                }
            }

            is MainAction.OnDetailsFavoriteToggle -> {
                viewModelScope.launch {
                    try {
                        toggleFavoriteProcedureUseCase(action.procedure)
                        _viewState.update { state ->
                            val procedureDetails =
                                if (state.procedureDetails?.id == action.procedure.id) {
                                    state.procedureDetails.copy(
                                        isFavorite = action.procedure.isFavorite.not(),
                                    )
                                } else {
                                    state.procedureDetails
                                }
                            state.copy(
                                procedureDetails = procedureDetails,
                                procedures = state.procedures.map { procedure ->
                                    if (procedure.id == action.procedure.id) {
                                        procedure.copy(isFavorite = procedure.isFavorite.not())
                                    } else {
                                        procedure
                                    }
                                },
                                favoriteToggledEvent = FavoriteToggledEvent(
                                    procedureName = action.procedure.name,
                                    isFavorite = action.procedure.isFavorite.not(),
                                ),
                            )
                        }
                    } catch (e: Exception) {
                        Log.e("MainViewModel", e.message, e)
                        _viewState.update { state ->
                            state.copy(
                                errorMessageEvent = ErrorMessageEvent(e.message)
                            )
                        }
                    }
                }
            }

            is MainAction.OnPreviewFavoriteToggle -> {
                viewModelScope.launch {
                    _viewState.update { it.copy(isLoading = true) }
                    try {
                        toggleFavoriteProcedureUseCase(action.procedure)
                        _viewState.update { state ->
                            state.copy(
                                procedures = state.procedures.map { procedure ->
                                    if (procedure.id == action.procedure.id) {
                                        procedure.copy(isFavorite = procedure.isFavorite.not())
                                    } else {
                                        procedure
                                    }
                                },
                                favoriteToggledEvent = FavoriteToggledEvent(
                                    procedureName = action.procedure.name,
                                    isFavorite = action.procedure.isFavorite.not(),
                                ),
                                isLoading = false,
                            )
                        }
                    } catch (e: Exception) {
                        Log.e("MainViewModel", e.message, e)
                        _viewState.update { state ->
                            state.copy(
                                isLoading = false,
                                errorMessageEvent = ErrorMessageEvent(e.message)
                            )
                        }
                    }
                }
            }

            MainAction.OnFavoriteEventConsumed -> {
                _viewState.update { state ->
                    state.copy(favoriteToggledEvent = null)
                }
            }

            MainAction.OnErrorEventConsumed -> {
                _viewState.update { state ->
                    state.copy(errorMessageEvent = null)
                }
            }

            MainAction.OnProceduresRefresh -> {
                fetchProcedures()
            }
        }
    }

    private fun fetchProcedures() {
        viewModelScope.launch {
            _viewState.update { state ->
                state.copy(isLoading = true)
            }
            try {
                _viewState.update { state ->
                    state.copy(
                        procedures = fetchProcedurePreviewsUseCase(),
                        isLoading = false,
                    )
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", e.message, e)
                _viewState.update { state ->
                    state.copy(
                        isLoading = false,
                        errorMessageEvent = ErrorMessageEvent(e.message)
                    )
                }
            }
        }
    }
}
