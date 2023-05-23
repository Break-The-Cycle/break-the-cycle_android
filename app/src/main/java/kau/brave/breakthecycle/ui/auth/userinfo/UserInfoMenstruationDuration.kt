package kau.brave.breakthecycle.ui.auth.userinfo

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kau.brave.breakthecycle.R
import kau.brave.breakthecycle.ui.model.ApplicationState
import kau.brave.breakthecycle.utils.rememberApplicationState
import kau.brave.breakthecycle.theme.Gray300
import kau.brave.breakthecycle.theme.Main
import kau.brave.breakthecycle.theme.White
import kau.brave.breakthecycle.utils.Constants.MAXIMUM_MENSTURATION_PEROID
import kau.brave.breakthecycle.utils.Constants.MINIMUM_MENSTURATION_PEROID
import kau.brave.breakthecycle.utils.Constants.USERINFO_MENSTRUATION_START_DATE_ROUTE


@Composable
fun UserInfoMenstruationDuration(
    appState: ApplicationState = rememberApplicationState(),
    viewModel: UserInfoViewModel = hiltViewModel()
) {

    val duration by viewModel.period

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
                    text = "생리 기간을 설정해주세요.",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 84.dp)
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_minus_24),
                            contentDescription = "IC_MINUS",
                            modifier = Modifier
                                .size(40.dp)
                                .clickable {
                                    if (duration > MINIMUM_MENSTURATION_PEROID) {
                                        viewModel.updateDuration(duration - 1)
                                    }
                                },
                            contentScale = ContentScale.Fit
                        )

                        Box(
                            modifier = Modifier.size(150.dp)
                        ) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                drawCircle(color = Main)
                            }
                            Text(
                                text = duration.toString(),
                                fontSize = 60.sp,
                                color = White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                        Image(
                            painter = painterResource(id = R.drawable.ic_plus_24),
                            contentDescription = "IC_PLUS",
                            modifier = Modifier
                                .size(40.dp)
                                .clickable {
                                    if (duration < MAXIMUM_MENSTURATION_PEROID) {
                                        viewModel.updateDuration(duration + 1)
                                    }
                                },
                            contentScale = ContentScale.Fit
                        )
                    }

                    Text(
                        text = "최소 3일 ~최대 7일로 설정 가능합니다.",
                        fontSize = 14.sp,
                        color = Gray300,
                        modifier = Modifier.padding(top = 20.dp)
                    )
                }

                Button(
                    onClick = {
                        appState.navigate(USERINFO_MENSTRUATION_START_DATE_ROUTE)
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