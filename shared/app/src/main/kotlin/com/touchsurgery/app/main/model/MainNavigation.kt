package com.touchsurgery.app.main.model

import androidx.annotation.DrawableRes
import com.touchsurgery.app.R

enum class MainNavigation(@DrawableRes val iconResource: Int) {
    PROCEDURES(R.drawable.ic_home),
    FAVORITES(R.drawable.ic_favorite_outline_24),
}
