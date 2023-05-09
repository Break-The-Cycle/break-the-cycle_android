package kau.brave.breakthecycle.ui.model

import androidx.compose.ui.graphics.Color
import kau.brave.breakthecycle.utils.BraveDate
import java.util.*

enum class DayOfWeek(val day: Int, val dayName: String, val color: Color) {
    SUNDAY(1, "일", Color.Red),
    MONDAY(2, "월", Color.Black),
    TUESDAY(3, "화", Color.Black),
    WEDNESDAY(4, "수", Color.Black),
    THURSDAY(5, "목", Color.Black),
    FRIDAY(6, "금", Color.Black),
    SATURDAY(7, "토", Color.Blue);

    companion object {
        fun find(day: Int): String = values().find { it.day == day }?.dayName ?: ""
        fun getDayOfWeekFromDate(year: Int, month: Int, day: Int): DayOfWeek {
            val calendar = Calendar.getInstance()
            calendar.set(year, month - 1, day) // month는 0부터 시작하므로 1을 빼줍니다.

            return when (calendar.get(Calendar.DAY_OF_WEEK)) {
                Calendar.SUNDAY -> SUNDAY
                Calendar.MONDAY -> MONDAY
                Calendar.TUESDAY -> TUESDAY
                Calendar.WEDNESDAY -> WEDNESDAY
                Calendar.THURSDAY -> THURSDAY
                Calendar.FRIDAY -> FRIDAY
                Calendar.SATURDAY -> SATURDAY
                else -> throw IllegalArgumentException("Invalid day of week")
            }
        }

        fun getDayOfWeekFromDate(date: BraveDate): DayOfWeek {
            val calendar = Calendar.getInstance()
            calendar.set(date.year, date.month - 1, date.day) // month는 0부터 시작하므로 1을 빼줍니다.

            return when (calendar.get(Calendar.DAY_OF_WEEK)) {
                Calendar.SUNDAY -> SUNDAY
                Calendar.MONDAY -> MONDAY
                Calendar.TUESDAY -> TUESDAY
                Calendar.WEDNESDAY -> WEDNESDAY
                Calendar.THURSDAY -> THURSDAY
                Calendar.FRIDAY -> FRIDAY
                Calendar.SATURDAY -> SATURDAY
                else -> throw IllegalArgumentException("Invalid day of week")
            }
        }
    }
}