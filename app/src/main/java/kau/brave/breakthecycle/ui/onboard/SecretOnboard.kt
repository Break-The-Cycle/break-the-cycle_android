package kau.brave.breakthecycle.ui.onboard

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kau.brave.breakthecycle.R
import kau.brave.breakthecycle.ui.model.ApplicationState
import kau.brave.breakthecycle.utils.rememberApplicationState
import kau.brave.breakthecycle.ui.auth.components.SignInGraphBottomConfirmButton
import kau.brave.breakthecycle.ui.component.HeightSpacer
import kau.brave.breakthecycle.theme.Gray300
import kau.brave.breakthecycle.theme.Main
import kau.brave.breakthecycle.theme.White
import kau.brave.breakthecycle.utils.Constants.SECERET_ONBOARD_ROUTE
import kau.brave.breakthecycle.utils.Constants.USERINFO_GRAPH
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SecretOnboard(
    appState: ApplicationState = rememberApplicationState(),
    init: Boolean,
    @DrawableRes images: List<Int> = listOf(
        R.mipmap.img_secret_onboard_enter,
        R.mipmap.img_secret_onboard_dialog,
        R.mipmap.img_secret_onboard_dialog,
        R.mipmap.img_secret_onboard_report,
        R.mipmap.img_secret_onboard_setting
    ),
    texts: List<String> = listOf(
        "로고를 5번 클릭하면 시크릿 모드로 진입할 수 있어요!",
        "시크릿모드에서는 캘린더에서 날짜를 선택하면\n나만 볼 수 있는 일기를 작성할 수 있어요.",
        "한번 작성한 일기는 삭제, 수정이 불가능한걸 알아주세요.",
        "시크릿 모드에서는 위급상황일 때 주변 지인 및 경찰에\n신고할 수 있는 버튼이 보여져요.",
        "관련 설정은 시크릿모드 마이페이지에서 할 수 있어요."
    )
) {

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Main),
    ) {
        Image(
            painter = painterResource(id = R.mipmap.img_login_background),
            contentDescription = "IMG_LOGIN_BACKGROUND",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
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
                HeightSpacer(dp = 80.dp)
                Text(
                    text = "쉿!",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "우리만의 비밀 기능이예요!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = texts[pagerState.currentPage], fontSize = 14.sp, color = Gray300)
                HorizontalPager(
                    state = pagerState,
                    count = images.size,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(vertical = 20.dp, horizontal = 30.dp)
                ) {
                    Image(
                        painter = painterResource(id = images[it]),
                        contentDescription = "IMG_ONBOARD_CYCLE",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                }
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp),
                    activeColor = Main,
                    inactiveColor = Gray300
                )

                SignInGraphBottomConfirmButton(
                    onClick = {
                        if (pagerState.currentPage == images.size - 1) {
                            if (init) {
                                appState.navController.navigate(USERINFO_GRAPH) {
                                    popUpTo(SECERET_ONBOARD_ROUTE) {
                                        inclusive = true
                                    }
                                }
                            } else appState.popBackStack()
                        } else {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    },
                    enabled = true
                )
            }
        }
    }

}