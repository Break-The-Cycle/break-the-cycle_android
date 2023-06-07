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

            if (RoseDaysApplication.isSecretMode.value) {
                Box(
                    modifier = Modifier
                        .background(day.getBackgroundColor(selectedDay = selectedDay))
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
                            selectedDay = selectedDay,
                            defaultColor = DayOfWeek.getDayOfWeekFromDate(day).color
                        ),
                    )

                    if (violentDays.contains(day)) {
                        Canvas(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(10.dp)
                        ) {
                            drawCircle(
                                color = IOSRed,
                                radius = 10f
                            )
                        }
                    }
                }
            } else {
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

@Composable
private fun RowScope.DateItem(
    day: BraveDate,
    selectedDay: BraveDate,
    menstruationDays: List<BraveDate>,
    childBearingDays: List<BraveDate>,
    ovulationDays: List<BraveDate>,
    setSelectDay: (BraveDate) -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                day.getBackgroundColor(
                    selectedDay,
                    menstruationDays,
                    childBearingDays,
                    ovulationDays
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
                ovulationDays,
                DayOfWeek.getDayOfWeekFromDate(day).color
            ),
        )
    }
}