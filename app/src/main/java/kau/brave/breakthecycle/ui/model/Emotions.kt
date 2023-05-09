package kau.brave.breakthecycle.ui.model

import androidx.annotation.DrawableRes
import kau.brave.breakthecycle.R

enum class Emotions(
    val title: String,
    @DrawableRes val defaultIcon: Int,
    @DrawableRes val coloredIcon: Int
) {
    VERY_SAD("매우 우울", R.mipmap.ic_very_sad, R.mipmap.ic_colored_very_sad),
    SAD("우울", R.mipmap.ic_sad, R.mipmap.ic_colored_sad),
    NORMAL("보통", R.mipmap.ic_normal, R.mipmap.ic_colored_normal),
    HAPPY("행복", R.mipmap.ic_happy, R.mipmap.ic_colored_happy),
    VERY_HAPPY("매우 행복", R.mipmap.ic_very_happy, R.mipmap.ic_colored_very_happy),
    NONE("", R.mipmap.ic_very_happy, R.mipmap.ic_colored_very_happy),

}