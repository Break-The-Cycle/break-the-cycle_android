package kau.brave.breakthecycle.ui.calendar.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kau.brave.breakthecycle.RoseDaysApplication
import kau.brave.breakthecycle.domain.model.BraveDate
import kau.brave.breakthecycle.theme.Gray400
import kau.brave.breakthecycle.theme.IOSRed
import kau.brave.breakthecycle.ui.model.DayOfWeek

@Composable
fun CalendarRow(
    modifier: Modifier = Modifier,
    days: List<BraveDate>,
    selectedDay: BraveDate,
    setSelectDay: (BraveDate) -> Unit,
    menstruationDays: List<BraveDate>,
    childBearingDays: List<BraveDate>,
    ovulationDays: List<BraveDate>,
    violentDays: List<BraveDate>
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        for (day in days) {
            // 시크릿 모드일 때
            if (RoseDaysApplication.isSecretMode.value) {
                SescretDateItem(
                    day = day,
                    selectedDay = selectedDay,
                    setSelectDay = setSelectDay,
                    violentDays = violentDays
                )
            } else {
                // 시크릿 모드가 아닐 때
                DateItem(
                    day = day,
                    selectedDay = selectedDay,
                    menstruationDays = menstruationDays,
                    childBearingDays = childBearingDays,
                    ovulationDays = ovulationDays,
                    setSelectDay = setSelectDay
                )
            }
        }
    }
}
