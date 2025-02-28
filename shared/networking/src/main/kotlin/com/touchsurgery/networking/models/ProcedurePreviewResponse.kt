package com.touchsurgery.networking.models

import com.touchsurgery.networking.serialization.InstantJson
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class ProcedurePreviewResponse(
    @JsonNames("uuid") val uuid: String,
    @JsonNames("name") val name: String,
    @JsonNames("icon") val icon: IconResponse,
    @JsonNames("phases") val phases: List<String>,
    @JsonNames("date_published") val datePublished: InstantJson,
    @JsonNames("duration") val duration: Int,
)
