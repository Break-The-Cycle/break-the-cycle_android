@file:OptIn(ExperimentalFoundationApi::class)

package kau.brave.breakthecycle.ui.calendar.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kau.brave.breakthecycle.R
import kau.brave.breakthecycle.domain.model.BraveDate
import kau.brave.breakthecycle.theme.Main
import java.util.*


@Composable
fun CalendarView(
    selectedDay: BraveDate,
    setSelectedDay: (BraveDate) -> Unit,
    updateRange: (BraveDate, BraveDate) -> Unit,
    menstruationDays: List<BraveDate>,
    childBearingDays: List<BraveDate>,
    ovulationDays: List<BraveDate>
) {
    var calendar by remember {
        mutableStateOf<Calendar>(Calendar.getInstance())
    }

    var month by remember {
        mutableStateOf(calendar.get(Calendar.MONTH) + 1)
    }

    val days = remember {
        mutableStateListOf<BraveDate>()
    }

    var date by remember {
        mutableStateOf("${calendar.get(Calendar.YEAR)}년 ${calendar.get(Calendar.MONTH) + 1}월")
    }

    LaunchedEffect(key1 = month) {
        days.clear()

        /** 이번 달 날짜 가져오기 */
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        date = "${calendar.get(Calendar.YEAR)}년 ${calendar.get(Calendar.MONTH) + 1}월"
        days.addAll(
            (1..calendar.getActualMaximum(Calendar.DAY_OF_MONTH)).map {
                BraveDate(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH) + 1,
                    it
                )
            }
        )
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

        /** 지난 달 날짜 가져오기 */
        if (dayOfWeek != Calendar.SUNDAY) {
            val lastMonth = calendar.clone() as Calendar
            lastMonth.add(Calendar.MONTH, -1)
            val emptyDays = (1 until dayOfWeek).map {
                BraveDate(
                    lastMonth.get(Calendar.YEAR),
                    lastMonth.get(Calendar.MONTH) + 1,
                    lastMonth.getActualMaximum(Calendar.DAY_OF_MONTH) - it + 1
                )
            }.reversed()
            days.addAll(0, emptyDays)
        }

        /** 다음 달 날짜 가져오기 */
        val nextMonth = calendar.clone() as Calendar
        nextMonth.add(Calendar.MONTH, 1)
        nextMonth.set(Calendar.DAY_OF_MONTH, 1)
        val nextDayOfWeek = nextMonth.get(Calendar.DAY_OF_WEEK)
        if (nextDayOfWeek != Calendar.SUNDAY) {
            val emptyDays = (1..(8 - nextDayOfWeek)).map {
                BraveDate(
                    nextMonth.get(Calendar.YEAR),
                    nextMonth.get(Calendar.MONTH) + 1,
                    it
                )
            }
            days.addAll(emptyDays)
        }

        updateRange(days.first(), days.last())
    }

    /** 년 월, 달력 버튼 */
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier.size(50.dp), onClick = {
                calendar = calendar.apply {
                    add(Calendar.MONTH, -1)
                }
                month = if (month == 1) 12 else (month - 1)
            }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_arrow_24),
                contentDescription = "IC_ARROW",
                tint = Main,
                modifier = Modifier.size(36.dp)
            )
        }
        Text(
            text = date,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Main
        )

        IconButton(
            modifier = Modifier.size(50.dp), onClick = {
                calendar = calendar.apply {
                    add(Calendar.MONTH, 1)
                }
                month = if (month == 12) 1 else (month + 1)
            }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_arrow_24),
                contentDescription = "IC_ARROW",
                tint = Main,
                modifier = Modifier
                    .size(36.dp)
                    .rotate(180f)
            )
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 20.dp)
    ) {
        itemsIndexed(
            items = days.chunked(7),
            key = { _, week -> week.first().day }
        ) { _, week ->
            CalendarRow(
                modifier = Modifier.animateItemPlacement(),
                days = week,
                selectedDay = selectedDay,
                setSelectDay = setSelectedDay,
                menstruationDays = menstruationDays,
                childBearingDays = childBearingDays,
                ovulationDays = ovulationDays
            )
        }
    }

}

