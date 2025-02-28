package com.touchsurgery.networking.models

import com.touchsurgery.networking.serialization.InstantJson
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class ProcedureDetailsResponse(
    @JsonNames("uuid") val uuid: String,
    @JsonNames("name") val name: String,
    @JsonNames("icon") val icon: IconResponse,
    @JsonNames("phases") val phases: List<Phase>,
    @JsonNames("date_published") val datePublished: InstantJson,
    @JsonNames("duration") val duration: Int,
) {

    @Serializable
    data class Phase(
        @JsonNames("uuid") val uuid: String,
        @JsonNames("name") val name: String,
        @JsonNames("icon") val icon: IconResponse,
    )
}
