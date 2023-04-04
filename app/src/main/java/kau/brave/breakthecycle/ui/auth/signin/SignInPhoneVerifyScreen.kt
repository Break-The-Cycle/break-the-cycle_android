package kau.brave.breakthecycle.ui.auth.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kau.brave.breakthecycle.R
import kau.brave.breakthecycle.domain.model.ApplicationState
import kau.brave.breakthecycle.domain.rememberApplicationState
import kau.brave.breakthecycle.ui.component.CustomTextField
import kau.brave.breakthecycle.ui.component.HeightSpacer
import kau.brave.breakthecycle.ui.theme.Error_Color
import kau.brave.breakthecycle.ui.theme.Main
import kau.brave.breakthecycle.ui.theme.White
import kau.brave.breakthecycle.utils.Constants.ONBOARD_ROUTE

@Preview
@Composable
fun SignInPhoneVerify(appstate: ApplicationState = rememberApplicationState()) {

    var phone by remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Main),
    ) {
        Box(
            modifier = Modifier
                .padding(bottom = 65.dp)
                .clip(RoundedCornerShape(bottomEnd = 70.dp))
                .fillMaxSize()
                .background(White),
        ) {
            Column(
                modifier = Modifier
                    .statusBarsPadding()
                    .fillMaxSize()
                    .padding(horizontal = 28.dp)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                ) {
                    IconButton(
                        onClick = {
                            appstate.popBackStack()
                        },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                            contentDescription = "IC_ARROW_BACK"
                        )
                    }
                }

                HeightSpacer(dp = 54.dp)

                Text(text = "전화번호 인증을\n진행해 주세요.", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                HeightSpacer(dp = 70.dp)

                Text(text = "전화번호", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    CustomTextField(
                        modifier = Modifier.weight(5f),
                        value = phone,
                        placeholderText = "전화번호를 입력해주세요.",
                        onvalueChanged = { phone = it }
                    )
                    Text(
                        text = "인증 번호 요청",
                        modifier = Modifier
                            .weight(3f)
                            .clip(RoundedCornerShape(5.dp))
                            .background(Main)
                            .height(IntrinsicSize.Max)
                            .padding(vertical = 6.dp)
                            .clickable {

                            },
                        color = White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
                HeightSpacer(dp = 30.dp)
                Text(text = "인증번호", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Row(
                    modifier = Modifier.wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    CustomTextField(
                        modifier = Modifier.weight(5f),
                        value = phone,
                        placeholderText = "인증번호를 입력해주세요.",
                        onvalueChanged = { phone = it }
                    )
                    Text(
                        text = "인증",
                        modifier = Modifier
                            .weight(3f)
                            .clip(RoundedCornerShape(5.dp))
                            .background(Main)
                            .padding(vertical = 6.dp)
                            .clickable {

                            },
                        color = White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
                Text(text = "03:00", color = Error_Color, fontSize = 14.sp)

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 90.dp),
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Main),
                    onClick = {
                        appstate.navController.navigate(ONBOARD_ROUTE)
                    }) {
                    Text(
                        text = "완료", color = White, fontSize = 16.sp, fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 3.dp)
                    )
                }
            }
        }
    }
}