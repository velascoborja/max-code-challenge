package com.touchsurgery.procedures.models

import com.touchsurgery.database.entities.IconEntity
import com.touchsurgery.networking.models.IconResponse

data class IconModel(
    val id: String,
    val url: String,
    val version: Long,
) {

    companion object {

        fun fromResponse(response: IconResponse) = IconModel(
            id = response.uuid,
            url = response.url,
            version = response.version,
        )

        fun fromEntity(entity: IconEntity) = IconModel(
            id = entity.iconId,
            url = entity.url,
            version = entity.version,
        )

        fun IconModel.toEntity() = IconEntity(
            iconId = id,
            url = url,
            version = version,
        )
    }
}
