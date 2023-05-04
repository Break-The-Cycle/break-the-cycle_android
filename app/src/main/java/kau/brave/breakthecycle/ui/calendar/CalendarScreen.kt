@file:OptIn(ExperimentalMaterialApi::class)

package kau.brave.breakthecycle.ui.calendar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kau.brave.breakthecycle.R
import kau.brave.breakthecycle.RoseDaysApplication.Companion.isSecretMode
import kau.brave.breakthecycle.theme.Gray300
import kau.brave.breakthecycle.ui.model.ApplicationState
import kau.brave.breakthecycle.utils.rememberApplicationState
import kau.brave.breakthecycle.ui.calendar.viewmodel.CalendarViewModel
import kau.brave.breakthecycle.theme.Main
import kau.brave.breakthecycle.ui.auth.userinfo.BraveDate
import kau.brave.breakthecycle.ui.component.BraveLogoIcon
import kau.brave.breakthecycle.ui.model.DayOfWeek
import kotlinx.coroutines.launch
import java.util.*

@Preview
@Composable
fun CalendarScreen(appState: ApplicationState = rememberApplicationState()) {

    val viewModel: CalendarViewModel = hiltViewModel()
    val selectedDay by viewModel.mensturationDay
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()

    val scope = rememberCoroutineScope()
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val localDensity = LocalDensity.current
    var columnHeightDp by remember {
        mutableStateOf(0.dp)
    }
    var pickHeight by remember {
        mutableStateOf(0.dp)
    }

    LaunchedEffect(key1 = columnHeightDp) {
        pickHeight = screenHeight - columnHeightDp + 86.dp
    }

//    val dummyDiary = List(3) { "테스트$it" }
    val dummyDiary = emptyList<String>()

    BottomSheetScaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        sheetPeekHeight = pickHeight, // 바텀 네비게이션바
        sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        scaffoldState = bottomSheetScaffoldState,
        sheetElevation = 0.dp,
        sheetContent = {
            Column(
                modifier = Modifier
                    .height(if (isSecretMode.value) screenHeight - 180.dp else pickHeight)
                    .fillMaxWidth()
                    .padding(top = 15.dp)
                    .background(Color.White)
                    .padding(horizontal = 24.dp)
            ) {
                Box(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .align(Alignment.CenterHorizontally)
                        .size(80.dp, 5.dp)
                        .clip(RoundedCornerShape(50))
                        .background(Gray300)
                )

                if (isSecretMode.value) {
                    Text(
                        text = "${selectedDay.second}월 ${selectedDay.third}일의 일기",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(20.dp)
                    )
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (dummyDiary.isEmpty()) {
                            item {
                                Text(
                                    text = "작성된 일기가 없습니다.",
                                    fontSize = 16.sp,
                                    color = Gray300,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                            item {
                                Button(
                                    colors = ButtonDefaults.buttonColors(Main),
                                    shape = RoundedCornerShape(10.dp),
                                    onClick = {

                                    }) {
                                    Text(
                                        text = "일기 작성하기", fontSize = 18.sp,
                                        color = Color.White, fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                        items(dummyDiary) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(2.2f)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_launcher_background),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(RoundedCornerShape(20.dp)),
                                    contentScale = ContentScale.Crop
                                )
                                Column(
                                    modifier = Modifier
                                        .align(Alignment.TopStart)
                                        .padding(20.dp),
                                ) {
                                    Text(
                                        text = it,
                                        fontSize = 18.sp,
                                        color = Color.White
                                    )
                                    Text(
                                        text = "content",
                                        fontSize = 18.sp,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Min),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "오늘은 생리 N일차 입니다.",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                                Text(text = "#금일의 임신 가능성 : 상")
                            }

                            Column(
                                modifier = Modifier.fillMaxHeight(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.img_logo_small),
                                    contentDescription = "IC_ADD"
                                )
                                Text(text = "사랑한 날")
                            }
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            Text(
                                text = "오늘의 기분 기록하기",
                                fontSize = 14.sp,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50))
                                    .border(1.dp, Main, RoundedCornerShape(50))
                                    .clickable {
                                        // TODO 기분 기록 
                                    }
                                    .padding(10.dp, 5.dp)

                            )
                            Text(
                                text = "이날 생리를 시작했어요.",
                                fontSize = 14.sp,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50))
                                    .border(1.dp, Main, RoundedCornerShape(50))
                                    .clickable {
                                        // TODO 금일 생리 주기로 설정
                                    }
                                    .padding(10.dp, 5.dp)

                            )
                        }
                    }
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .onGloballyPositioned {
                    columnHeightDp = with(localDensity) { it.size.height.toDp() }
                },
        ) {
            BraveLogoIcon {
                scope.launch {
                    appState.showSnackbar("시크릿 모드에 진입했습니다.")
                    bottomSheetScaffoldState.bottomSheetState.collapse()
                }
            }
            CalendarView(
                setSelectedDay = viewModel::updateMensturationDay,
                selectedDay = selectedDay,
            )
        }
    }
}


@Composable
fun CalendarView(
    selectedDay: BraveDate,
    setSelectedDay: (BraveDate) -> Unit,
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

    /** 년 월, 달력 버튼 */
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
        itemsIndexed(items = days.chunked(7)) { _, week ->
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
                Text(
                    text = day.third.toString(),
                    modifier = Modifier
                        .align(Alignment.Center),
                    fontWeight = FontWeight.Bold,
                    color = DayOfWeek.getDayOfWeekFromDate(day).color,
                )
            }
        }
    }
}