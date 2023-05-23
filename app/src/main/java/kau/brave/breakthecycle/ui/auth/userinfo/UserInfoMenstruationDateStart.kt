package kau.brave.breakthecycle.ui.auth.userinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kau.brave.breakthecycle.utils.rememberApplicationState
import kau.brave.breakthecycle.ui.model.ApplicationState
import kau.brave.breakthecycle.theme.Main
import kau.brave.breakthecycle.theme.White
import kau.brave.breakthecycle.theme.Gray300
import kau.brave.breakthecycle.ui.auth.components.OnboardCalendarView
import kau.brave.breakthecycle.utils.Constants.USERINFO_MENSTRUATION_END_DATE_ROUTE


@Preview
@Composable
fun UserInfoMenstruationDateStart(
    appState: ApplicationState = rememberApplicationState(),
    viewModel: UserInfoViewModel = hiltViewModel()
) {

    val startdDate by viewModel.mensturationStartDate

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
                    text = "생리 주기를 알려주세요",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 84.dp)
                )
                Text(text = "최근 생리는 언제 시작하셨나요?", fontSize = 16.sp, color = Gray300)

                OnboardCalendarView(
                    setSelectedDay = viewModel::updateMensturationStartDay,
                    selectedDay = startdDate,
                    modifier = Modifier.padding(top = 10.dp)
                )

                Text(
                    text = startdDate.format(), fontSize = 24.sp,
                    fontWeight = FontWeight.Bold, color = Main, modifier = Modifier
                        .align(
                            Alignment.CenterHorizontally
                        )
                        .padding(top = 10.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                        appState.navigate(USERINFO_MENSTRUATION_END_DATE_ROUTE)
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



