package kau.brave.breakthecycle.ui.calendar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kau.brave.breakthecycle.R
import kau.brave.breakthecycle.domain.model.ApplicationState
import kau.brave.breakthecycle.ui.calendar.viewmodel.CalendarViewModel
import kau.brave.breakthecycle.ui.theme.Gray300
import kau.brave.breakthecycle.ui.theme.Main
import kau.brave.breakthecycle.utils.bottomBorder
import java.util.*

@Composable
fun CalendarScreen(appState: ApplicationState) {

    val viewModel: CalendarViewModel = hiltViewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
    ) {

        Image(
            painter = painterResource(id = R.drawable.img_logo_small),
            contentDescription = "IMG_LOGO_SMALL",
            modifier = Modifier
                .size(56.dp)
                .align(Alignment.CenterHorizontally)
        )

        CalendarView()

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(top = 15.dp)
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(Color.White)
                .border(1.dp, Gray300, RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .bottomBorder(2.dp, Color.White)
                .padding(horizontal = 24.dp)
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally)
                    .size(80.dp, 5.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color.Black)
            )

            Text(
                text = "오늘의 일기",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 20.dp)
            )

        }
    }

}

@Composable
fun CalendarView() {
    var calendar by remember {
        mutableStateOf<Calendar>(Calendar.getInstance())
    }

    var month by remember {
        mutableStateOf(calendar.get(Calendar.MONTH) + 1)
    }

    val days = remember {
        mutableStateListOf<Int>()
    }

    var date by remember {
        mutableStateOf("${calendar.get(Calendar.YEAR)}년 ${calendar.get(Calendar.MONTH) + 1}월")
    }

    LaunchedEffect(key1 = month) {
        days.clear()
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        date = "${calendar.get(Calendar.YEAR)}년 ${calendar.get(Calendar.MONTH) + 1}월"

        days.addAll((1..calendar.getActualMaximum(Calendar.DAY_OF_MONTH)).toList())
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

        /** 지난 달 날짜 가져오기 */
        if (dayOfWeek != Calendar.SUNDAY) {
            val lastMonth = calendar.clone() as Calendar
            lastMonth.add(Calendar.MONTH, -1)
            val emptyDays = (1 until dayOfWeek).map {
                lastMonth.getActualMaximum(Calendar.DAY_OF_MONTH) - it + 1
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
                it
            }
            days.addAll(emptyDays)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 30.dp),
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
        itemsIndexed(items = days.chunked(7)) { index, week ->
            CalendarRow(days = week)
        }
    }
}


@Composable
fun CalendarRow(days: List<Int>) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        for (day in days) {
            DateCell(date = day)
        }
    }
}

@Composable
fun RowScope.DateCell(date: Int) {
    Box(
        modifier = Modifier
            .background(Color.White)
            .weight(1f)
            .aspectRatio(1f)
    ) {
        Text(
            text = date.toString(),
            modifier = Modifier.align(Alignment.Center),
            fontWeight = FontWeight.Bold
        )
    }
}
