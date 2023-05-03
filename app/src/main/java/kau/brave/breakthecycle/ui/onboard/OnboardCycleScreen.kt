@file:OptIn(ExperimentalPagerApi::class)

package kau.brave.breakthecycle.ui.auth.onboard

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
import kau.brave.breakthecycle.utils.Constants.SECERT_ONBOARD_GRAPH
import kotlinx.coroutines.launch

@Preview
@Composable
fun OnboardCycleScreen(appstate: ApplicationState = rememberApplicationState()) {

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
                    text = "다음 생리가 언제인지\n간단하게 알아봐요.",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "간단하게 다음 생리가 언제인지 예측할 수 있어요.", fontSize = 16.sp, color = Gray300)

                HorizontalPager(
                    state = pagerState,
                    count = 2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(vertical = 20.dp, horizontal = 30.dp)
                ) {
                    Image(
                        painter = painterResource(
                            id =
                            if (it == 0) R.mipmap.img_onboard_cycle
                            else R.mipmap.img_onboard_callendar
                        ),
                        contentDescription = "IMG_ONBOARD_CYCLE",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
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
                        if (pagerState.currentPage == 0) {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(1)
                            }
                        } else if (pagerState.currentPage == 1) {
                            appstate.navController.navigate(SECERT_ONBOARD_GRAPH)
                        }
                    },
                    enabled = true
                )
            }
        }
    }
}