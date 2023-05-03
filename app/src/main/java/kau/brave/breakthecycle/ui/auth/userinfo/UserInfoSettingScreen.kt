package kau.brave.breakthecycle.ui.auth.userinfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kau.brave.breakthecycle.R
import kau.brave.breakthecycle.ui.model.ApplicationState
import kau.brave.breakthecycle.theme.Main
import kau.brave.breakthecycle.theme.White
import kau.brave.breakthecycle.utils.Constants.USERINFO_MENSTRUATION_DURATION_ROUTE

@Composable
fun UserInfoSettingScreen(appstate: ApplicationState, viewModel: UserInfoViewModel) {

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

                Image(
                    painter = painterResource(id = R.drawable.img_logo),
                    contentDescription = "IMG_LOGO",
                    modifier = Modifier
                        .padding(top = 60.dp)
                        .align(Alignment.CenterHorizontally)
                        .size(100.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = "로즈 데이즈에 오신걸\n환영합니다.",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "시작하기전\n간단한 기본설정이 필요해요.",
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }

                Button(
                    onClick = {
                        appstate.navigate(USERINFO_MENSTRUATION_DURATION_ROUTE)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 110.dp),
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(Main)
                ) {
                    Text(
                        text = "좋아요",
                        color = White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}