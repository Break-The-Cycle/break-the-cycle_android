package kau.brave.breakthecycle.ui.model

import androidx.annotation.DrawableRes
import kau.brave.breakthecycle.R

sealed class Screen(
    val route: String,
    val title: String,
    @DrawableRes val drawableResId: Int,
    @DrawableRes val selecteddrawableResId: Int,
) {

    object Home :
        Screen(
            "nav-home",
            "홈",
            R.drawable.ic_baseline_home_36,
            R.drawable.ic_baseline_home_36,
        )

    object Calendar :
        Screen(
            "nav-map",
            "캘린더",
            R.drawable.ic_baseline_calendar_36,
            R.drawable.ic_baseline_calendar_36,
        )

    object Mypage :
        Screen(
            "nav-mypage",
            "마이 페이지",
            R.drawable.ic_baseline_mypage_36,
            R.drawable.ic_baseline_mypage_36,
        )

}