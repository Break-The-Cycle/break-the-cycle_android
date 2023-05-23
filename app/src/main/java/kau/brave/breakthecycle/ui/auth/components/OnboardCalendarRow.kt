package kau.brave.breakthecycle.ui.auth.components

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import kau.brave.breakthecycle.domain.model.BraveDate
import kau.brave.breakthecycle.theme.Main
import kau.brave.breakthecycle.ui.model.DayOfWeek

@Composable
fun OnboardCalendarRow(
    days: List<BraveDate>,
    selectedDay: BraveDate,
    setSelectDay: (BraveDate) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        for (day in days) {
            Box(
                modifier = Modifier
                    .background(Main)
                    .background(if (selectedDay == day) Main else Color.White)
                    .clickable { setSelectDay(day) }
                    .weight(1f)
                    .aspectRatio(1f)
            ) {
                Text(
                    text = day.day.toString(),
                    modifier = Modifier
                        .align(Alignment.Center),
                    fontWeight = FontWeight.Bold,
                    color = DayOfWeek.getDayOfWeekFromDate(day).color,
                )
            }
        }
    }
}