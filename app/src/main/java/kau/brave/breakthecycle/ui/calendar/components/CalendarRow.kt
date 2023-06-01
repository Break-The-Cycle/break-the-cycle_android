package kau.brave.breakthecycle.ui.calendar.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import kau.brave.breakthecycle.domain.model.BraveDate
import kau.brave.breakthecycle.ui.model.DayOfWeek
import kau.brave.breakthecycle.utils.Constants

@Composable
fun CalendarRow(
    days: List<BraveDate>,
    selectedDay: BraveDate,
    setSelectDay: (BraveDate) -> Unit,
    menstruationDays: List<BraveDate>,
    childBearingDays: List<BraveDate>
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        for (day in days) {
            Box(
                modifier = Modifier
                    .background(
                        day.getBackgroundColor(
                            selectedDay,
                            menstruationDays,
                            childBearingDays,
                        )
                    )
                    .clickable { setSelectDay(day) }
                    .weight(1f)
                    .aspectRatio(1f)
            ) {
                Text(
                    text = day.day.toString(),
                    modifier = Modifier
                        .align(Alignment.Center),
                    fontWeight = FontWeight.Bold,
                    color = day.getTextColor(
                        selectedDay,
                        menstruationDays,
                        childBearingDays,
                        DayOfWeek.getDayOfWeekFromDate(day).color
                    ),
                )
            }
        }
    }
}