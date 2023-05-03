package kau.brave.breakthecycle.ui.model

enum class DayOfWeek(val day: Int, val dayName: String) {
    SUNDAY(1, "일"),
    MONDAY(2, "월"),
    TUESDAY(3, "화"),
    WEDNESDAY(4, "수"),
    THURSDAY(5, "목"),
    FRIDAY(6, "금"),
    SATURDAY(7, "토");

    companion object {
        fun find(day: Int): String = values().find { it.day == day }?.dayName ?: ""
    }
}