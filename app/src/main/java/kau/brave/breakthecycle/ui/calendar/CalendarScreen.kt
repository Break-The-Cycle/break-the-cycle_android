package kau.brave.breakthecycle.ui.calendar

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kau.brave.breakthecycle.R
import kau.brave.breakthecycle.domain.model.ApplicationState
import kau.brave.breakthecycle.ui.calendar.viewmodel.CalendarViewModel
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

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Button(onClick = {
            calendar = calendar.apply {
                add(Calendar.MONTH, -1)
            }
            month = if (month == 1) 12 else (month - 1)
        }) {
            Text(text = "이전")
        }
        Text(text = date)
        Button(onClick = {
            calendar = calendar.apply {
                add(Calendar.MONTH, 1)
            }
            month = if (month == 12) 1 else (month + 1)
        }) {
            Text(text = "다음")
        }
    }


    LazyColumn(modifier = Modifier.fillMaxWidth()) {

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
    ) {
        Text(
            text = date.toString(),
            modifier = Modifier.align(Alignment.Center),
            fontWeight = FontWeight.Bold
        )
    }
}
