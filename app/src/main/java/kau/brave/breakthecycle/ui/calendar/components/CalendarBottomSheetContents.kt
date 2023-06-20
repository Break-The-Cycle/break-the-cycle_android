@file:OptIn(ExperimentalGlideComposeApi::class, ExperimentalPagerApi::class)

package kau.brave.breakthecycle.ui.calendar.components

import android.util.Base64
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
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kau.brave.breakthecycle.R
import kau.brave.breakthecycle.RoseDaysApplication
import kau.brave.breakthecycle.domain.model.BraveDate
import kau.brave.breakthecycle.domain.model.BraveDiary
import kau.brave.breakthecycle.theme.Gray300
import kau.brave.breakthecycle.theme.Gray600
import kau.brave.breakthecycle.theme.Main
import kau.brave.breakthecycle.ui.component.HeightSpacer
import kau.brave.breakthecycle.ui.model.DateType
import kau.brave.breakthecycle.utils.Constants


@Composable
fun CalendarBottomSheetContents(
    screenHeight: Dp,
    pickHeight: Dp,
    selectedDay: BraveDate,
    violentDiaries: List<BraveDiary>,
    navigateToDiaryWrite: () -> Unit,
    navigateToDiaryDetail: (BraveDate) -> Unit,
    updateDialogVisibiliy: (Boolean) -> Unit,
    selectedDateType: DateType,
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
                violentDiaries = violentDiaries,
                navigateToDiaryWrite = navigateToDiaryWrite,
                navigateToDiaryDetail = navigateToDiaryDetail,
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
    violentDiaries: List<BraveDiary>,
    navigateToDiaryWrite: () -> Unit,
    navigateToDiaryDetail: (BraveDate) -> Unit,
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
        if (violentDiaries.isEmpty()) {
            item {
                Text(
                    text = "작성된 일기가 없습니다.",
                    fontSize = 16.sp,
                    color = Gray300,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                )
            }
        }

        items(violentDiaries) { diary ->
            val imageByteArrays: List<ByteArray> = diary.images.map {
                Base64.decode(it, Base64.DEFAULT)
            }
            val pagerState = rememberPagerState()
            Column(
                modifier = Modifier.clickable {
                    navigateToDiaryDetail(selectedDay)
                }
            ) {
                if (imageByteArrays.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(2.2f)
                    ) {
                        HorizontalPager(
                            count = imageByteArrays.size,
                            modifier = Modifier
                                .fillMaxSize(),
                            state = pagerState
                        ) { page ->
                            GlideImage(
                                model = imageByteArrays[page],
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(Color.Gray),
                                contentScale = ContentScale.Crop
                            )
                        }
                        HorizontalPagerIndicator(
                            pagerState = pagerState,
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(10.dp),
                            activeColor = Color.White,
                            inactiveColor = Gray300
                        )
                    }
                    HeightSpacer(dp = 10.dp)
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = diary.title,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                    Text(
                        text = diary.contents.take(100),
                        fontSize = 16.sp,
                        color = Gray600
                    )
                }
            }
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
}