package kau.brave.breakthecycle.ui.auth.userinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kau.brave.breakthecycle.R
import kau.brave.breakthecycle.utils.rememberApplicationState
import kau.brave.breakthecycle.ui.model.ApplicationState
import kau.brave.breakthecycle.theme.Main
import kau.brave.breakthecycle.theme.White
import kau.brave.breakthecycle.ui.model.DayOfWeek
import kau.brave.breakthecycle.utils.Constants.SIGNIN_COMPLETE_ROUTE
import java.util.*


@Preview
@Composable
fun UserInfoMenstruationDate(
    appState: ApplicationState = rememberApplicationState(),
    viewModel: UserInfoViewModel = hiltViewModel()
) {

    val selectedDay by viewModel.mensturationDay

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Main),
    ) {
        Box(
            modifier = Modifier
                .padding(bottom = 44.dp)
                .statusBarsPadding()
                .fillMaxSize()
                .shadow(5.dp, RoundedCornerShape(topStart = 70.dp, bottomEnd = 70.dp))
                .clip(RoundedCornerShape(topStart = 70.dp, bottomEnd = 70.dp))
                .background(White),
        ) {
            Column(
                modifier = Modifier
                    .statusBarsPadding()
                    .fillMaxSize()
                    .background(White)
                    .padding(horizontal = 28.dp)
            ) {

                Text(
                    text = "최근 생리를 시작한\n날이 언제인가요?",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 84.dp)
                )

                CalendarView(
                    setSelectedDay = viewModel::updateMensturationDay,
                    selectedDay = selectedDay,
                    modifier = Modifier.padding(top = 10.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                        appState.navigate(SIGNIN_COMPLETE_ROUTE)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 110.dp),
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(Main)
                ) {
                    Text(
                        text = "다음",
                        color = White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}


@Composable
fun CalendarView(
    selectedDay: BraveDate,
    setSelectedDay: (BraveDate) -> Unit,
    modifier: Modifier
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
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        date = "${calendar.get(Calendar.YEAR)}년 ${calendar.get(Calendar.MONTH) + 1}월"

        days.addAll(
            (1..calendar.getActualMaximum(Calendar.DAY_OF_MONTH)).map {
                Triple(
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
                Triple(
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
                Triple(
                    nextMonth.get(Calendar.YEAR),
                    nextMonth.get(Calendar.MONTH) + 1,
                    it
                )
            }
            days.addAll(emptyDays)
        }
    }

    Row(
        modifier = modifier
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
            CalendarRow(
                days = week,
                selectedDay = selectedDay,
                setSelectDay = setSelectedDay
            )
        }
    }
}


@Composable
fun CalendarRow(
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
                val dayOfWeek = DayOfWeek.getDayOfWeekFromDate(day) // 요일 추출
                Text(
                    text = day.third.toString(),
                    modifier = Modifier
                        .align(Alignment.Center),
                    fontWeight = FontWeight.Bold,
                    color = if (dayOfWeek.day == 1) Color.Red else if (dayOfWeek.day == 7) Color.Blue else Color.Black,
                )
            }
        }
    }
}