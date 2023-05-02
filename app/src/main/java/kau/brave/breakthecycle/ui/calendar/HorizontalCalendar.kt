package kau.brave.breakthecycle.ui.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kau.brave.breakthecycle.ui.theme.Main
import java.time.LocalDate
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HorizontalCalendar(
    modifier: Modifier = Modifier,
    selectedDate: LocalDate = LocalDate.now(),
    onDayClicked: (LocalDate) -> Unit
) {
    val scrollState = rememberLazyListState()

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        val currentYearMonth = YearMonth.from(selectedDate)
        var currentDay = currentYearMonth.atDay(1)
        val days = mutableListOf<LocalDate>()
        for (i: Int in 1..currentYearMonth.lengthOfMonth()) {
            days.add(currentDay)
            currentDay = currentDay.plusDays(1)
        }

        LazyRow(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            state = scrollState
        ) {
            items(days) { today ->
                Day(
                    day = today,
                    selectedDate = selectedDate,
                    onDayClicked = onDayClicked
                )
            }
        }

        LaunchedEffect(selectedDate) {
            val scrollPosition =
                if (selectedDate.dayOfMonth - 4 < 0) 0 else selectedDate.dayOfMonth - 4

            scrollState.animateScrollToItem(scrollPosition)
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun Day(
    modifier: Modifier = Modifier,
    day: LocalDate,
    selectedDate: LocalDate,
    onDayClicked: (LocalDate) -> Unit
) {
    val selected = (day == selectedDate)

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = weekdays[day.dayOfWeek.value - 1])

            Spacer(modifier = Modifier.height(15.dp))

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(
                        color = if (selected) Main else Color.Transparent
                    )
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null,
                        onClick = { onDayClicked(day) }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = day.dayOfMonth.toString(),
                    color = if (selected) Color.White else Main
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewHorizontalCalendar() {
    HorizontalCalendar(onDayClicked = { })
}

val weekdays = listOf("월", "화", "수", "목", "금", "토", "일")