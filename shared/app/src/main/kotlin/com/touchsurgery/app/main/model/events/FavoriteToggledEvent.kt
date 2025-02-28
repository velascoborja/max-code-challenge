package com.touchsurgery.app.main.model.events

data class FavoriteToggledEvent(
    val procedureName: String,
    val isFavorite: Boolean,
)
