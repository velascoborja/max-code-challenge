package com.touchsurgery.app.main.model

import com.touchsurgery.procedures.models.ProcedureDetails
import com.touchsurgery.procedures.models.ProcedurePreview

sealed interface MainAction {

    @JvmInline
    value class OnProcedureClick(val procedureId: String) : MainAction

    @JvmInline
    value class OnProcedureClose(val procedure: ProcedureDetails) : MainAction

    @JvmInline
    value class OnPreviewFavoriteToggle(val procedure: ProcedurePreview) : MainAction

    @JvmInline
    value class OnDetailsFavoriteToggle(val procedure: ProcedureDetails) : MainAction

    data object OnFavoriteEventConsumed : MainAction

    data object OnErrorEventConsumed : MainAction

    data object OnProceduresRefresh : MainAction
}
