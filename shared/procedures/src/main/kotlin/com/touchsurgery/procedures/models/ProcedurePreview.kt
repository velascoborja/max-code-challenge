package com.touchsurgery.procedures.models

import com.touchsurgery.networking.models.ProcedurePreviewResponse
import kotlinx.datetime.Instant

data class ProcedurePreview(
    val id: String,
    val name: String,
    val icon: IconModel,
    val phases: List<String>,
    val datePublished: Instant,
    val duration: Int,
    val isFavorite: Boolean
) {
    companion object {

        fun fromResponse(
            response: ProcedurePreviewResponse,
            isFavorite: Boolean,
        ) = ProcedurePreview(
            id = response.uuid,
            name = response.name,
            icon = IconModel.fromResponse(response.icon),
            phases = response.phases,
            datePublished = response.datePublished,
            duration = response.duration,
            isFavorite = isFavorite,
        )
    }
}
