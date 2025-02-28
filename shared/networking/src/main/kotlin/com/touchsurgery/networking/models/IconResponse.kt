package com.touchsurgery.networking.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class IconResponse(
    @JsonNames("uuid") val uuid: String,
    @JsonNames("url") val url: String,
    @JsonNames("version") val version: Long,
)
