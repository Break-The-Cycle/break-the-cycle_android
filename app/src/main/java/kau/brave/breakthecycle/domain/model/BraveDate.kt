package kau.brave.breakthecycle.domain.model

import androidx.compose.ui.graphics.Color
import kau.brave.breakthecycle.theme.*

data class BraveDate(val year: Int, val month: Int, val day: Int) {


    fun getBackgroundColor(
        selectedDay: BraveDate = BraveDate(1999, 1, 1),
        menstruationDays: List<BraveDate> = emptyList(),
        childBearingDays: List<BraveDate> = emptyList(),
        ovulationsDays: List<BraveDate> = emptyList(),
    ): Color {
        return if (selectedDay == this) Sub1
        else if (ovulationsDays.contains(this)) Ovulation
        else if (menstruationDays.contains(this)) Menstrual
        else if (childBearingDays.contains(this)) ChildBearing
        else White
    }

    fun getTextColor(
        selectedDay: BraveDate = BraveDate(1999, 1, 1),
        menstruationDays: List<BraveDate> = emptyList(),
        childBearingDays: List<BraveDate> = emptyList(),
        ovulationsDays: List<BraveDate> = emptyList(),
        defaultColor: Color = Color.Black
    ): Color {
        return if (selectedDay == this || menstruationDays.contains(this) ||
            childBearingDays.contains(this) || ovulationsDays.contains(this)
        ) White
        else defaultColor
    }

    fun format(seperator: Char = '-'): String =
        "$year$seperator${"%02d".format(month)}$seperator${"%02d".format(day)}"


}