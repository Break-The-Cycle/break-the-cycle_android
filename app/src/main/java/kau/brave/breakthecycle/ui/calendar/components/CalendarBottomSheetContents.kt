package kau.brave.breakthecycle.ui.calendar.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kau.brave.breakthecycle.R
import kau.brave.breakthecycle.RoseDaysApplication
import kau.brave.breakthecycle.domain.model.BraveDate
import kau.brave.breakthecycle.theme.Gray300
import kau.brave.breakthecycle.theme.Main
import kau.brave.breakthecycle.ui.model.DateType


@Composable
fun CalendarBottomSheetContents(
    screenHeight: Dp,
    pickHeight: Dp,
    selectedDay: BraveDate,
    diaries: List<String>,
    navigateToDiaryWrite: () -> Unit,
    updateDialogVisibiliy: (Boolean) -> Unit,
    selectedDateType: DateType
) {
    Column(
        modifier = Modifier
            .height(if (RoseDaysApplication.isSecretMode.value) screenHeight - 180.dp else pickHeight)
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

        if (RoseDaysApplication.isSecretMode.value) {
            SecretBottomSheetContents(
                selectedDay = selectedDay,
                diaries = diaries,
                navigateToDiaryWrite = navigateToDiaryWrite
            )
        } else {
            BottomSheetContents(
                selectedDay = selectedDay,
                selectedDateType = selectedDateType,
                updateDialogVisibiliy = updateDialogVisibiliy,
            )
        }
    }
}

@Composable
private fun BottomSheetContents(
    selectedDay: BraveDate,
    selectedDateType: DateType,
    updateDialogVisibiliy: (Boolean) -> Unit,
) {

    var selected by remember {
        mutableStateOf(false)
    }

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
                    text = "${selectedDay.month}월 ${selectedDay.day}일 ${
                        when (selectedDateType) {
                            DateType.OVULATION -> "배란기"
                            DateType.MENSTRUATION -> "생리중"
                            DateType.CHILDBEARING -> "가임기"
                            DateType.NORMAL -> ""
                        }
                    }",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "#임신 확률 : ${
                        when (selectedDateType) {
                            DateType.MENSTRUATION -> "하"
                            DateType.OVULATION -> "상"
                            DateType.CHILDBEARING -> "중"
                            DateType.NORMAL -> "하"
                        }
                    }"
                )
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
                        updateDialogVisibiliy(true)
                    }
                    .padding(10.dp, 5.dp)
            )
        }
    }
}

@Composable
private fun SecretBottomSheetContents(
    selectedDay: BraveDate,
    diaries: List<String>,
    navigateToDiaryWrite: () -> Unit
) {
    Text(
        text = "${selectedDay.month}월 ${selectedDay.day}일의 일기",
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
        if (diaries.isEmpty()) {
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
                    onClick = navigateToDiaryWrite
                ) {
                    Text(
                        text = "일기 작성하기", fontSize = 18.sp,
                        color = Color.White, fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        items(diaries) {
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
}