package kau.brave.breakthecycle.domain.model

import androidx.compose.ui.graphics.Color
import kau.brave.breakthecycle.theme.*

data class BraveDate(val year: Int, val month: Int, val day: Int) {


    fun getBackgroundColor(
        selectedDay: BraveDate,
        tempMenDate: List<BraveDate>,
        tempBenDate: List<BraveDate>
    ): Color {
        return if (selectedDay == this) Sub1
        else if (tempMenDate.contains(this)) Menstrual
        else if (tempBenDate.contains(this)) ChildBearing
        else White
    }

    fun getTextColor(
        selectedDay: BraveDate,
        tempMenDate: List<BraveDate>,
        tempBenDate: List<BraveDate>,
        defaultColor: Color
    ): Color {
        return if (selectedDay == this || tempMenDate.contains(this) || tempBenDate.contains(this)) White
        else defaultColor
    }
}