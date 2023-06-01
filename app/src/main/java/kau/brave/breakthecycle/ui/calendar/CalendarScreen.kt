@file:OptIn(ExperimentalMaterialApi::class)

package kau.brave.breakthecycle.ui.calendar

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kau.brave.breakthecycle.R
import kau.brave.breakthecycle.RoseDaysApplication.Companion.isSecretMode
import kau.brave.breakthecycle.theme.Gray300
import kau.brave.breakthecycle.theme.Main
import kau.brave.breakthecycle.theme.White
import kau.brave.breakthecycle.ui.calendar.components.CalendarView
import kau.brave.breakthecycle.ui.calendar.viewmodel.CalendarViewModel
import kau.brave.breakthecycle.ui.component.BraveLogoIcon
import kau.brave.breakthecycle.ui.model.ApplicationState
import kau.brave.breakthecycle.ui.model.Emotions
import kau.brave.breakthecycle.utils.Constants.DIARY_WRITE_GRAPH
import kau.brave.breakthecycle.utils.rememberApplicationState
import kotlinx.coroutines.launch
import java.util.*

@SuppressLint("RememberReturnType")
@Preview
@Composable
fun CalendarScreen(appState: ApplicationState = rememberApplicationState()) {

    val viewModel: CalendarViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val localDensity = LocalDensity.current
    var contentsHeight by remember {
        mutableStateOf(0.dp)
    }
    var pickHeight by remember {
        mutableStateOf(0.dp)
    }
    var dialogVisiblity by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = contentsHeight) {
        pickHeight = screenHeight - contentsHeight + 106.dp
    }

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
                        text = "${uiState.selectedDay.month}월 ${uiState.selectedDay.day}일의 일기",
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
                                        appState.navigate(DIARY_WRITE_GRAPH)
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

                            var selected by remember {
                                mutableStateOf(false)
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .clickable {
                                        selected = !selected
                                    },
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(
                                    10.dp,
                                    Alignment.CenterVertically
                                ),
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_fill_check_28),
                                    contentDescription = "IC_CHECK",
                                    tint = if (selected) Main else Gray300,
                                )
                                Text(text = "사랑한 날")
                            }
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(bottom = 20.dp)
                        ) {
                            Text(
                                text = "오늘의 기분 기록하기",
                                fontSize = 14.sp,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50))
                                    .border(1.dp, Main, RoundedCornerShape(50))
                                    .clickable {
                                        dialogVisiblity = true
                                    }
                                    .padding(10.dp, 5.dp)
                            )
//                            Text(
//                                text = "이날 생리를 시작했어요.",
//                                fontSize = 14.sp,
//                                modifier = Modifier
//                                    .clip(RoundedCornerShape(50))
//                                    .border(1.dp, Main, RoundedCornerShape(50))
//                                    .clickable {
//                                        // TODO 금일 생리 주기로 설정
//                                    }
//                                    .padding(10.dp, 5.dp)
//                            )
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
                    contentsHeight = with(localDensity) { it.size.height.toDp() }
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
                selectedDay = uiState.selectedDay,
                menstruationDays = uiState.menstruationDays,
                childBearingDays = uiState.childBearingDays,
                ovulationDays = uiState.ovulationDays,
                updateRange = viewModel::updateRange,
            )
        }
    }

    if (dialogVisiblity) {
        var selectedEmotion by remember {
            mutableStateOf(Emotions.NONE)
        }
        Dialog(onDismissRequest = { dialogVisiblity = false }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                shape = RoundedCornerShape(20.dp),
                elevation = 10.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "오늘의 기분을 입력해 보아요.",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Emotions.values().forEach {
                            Image(
                                painter = painterResource(id = if (selectedEmotion == it) it.coloredIcon else it.defaultIcon),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clickable {
                                        selectedEmotion = it
                                    },
                                contentScale = ContentScale.Fit
                            )
                        }
                    }
                    Button(onClick = {
                        dialogVisiblity = false
                    }) {
                        Text(
                            text = "완료",
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 0.dp),
                            fontSize = 18.sp,
                            color = White
                        )
                    }
                }
            }

        }
    }
}

