package kau.brave.breakthecycle.ui.auth.signin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kau.brave.breakthecycle.domain.model.ApplicationState
import kau.brave.breakthecycle.domain.rememberApplicationState
import kau.brave.breakthecycle.ui.auth.components.SignInGraphBottomConfirmButton
import kau.brave.breakthecycle.ui.component.CustomTextField
import kau.brave.breakthecycle.ui.component.HeightSpacer
import kau.brave.breakthecycle.ui.theme.Disabled
import kau.brave.breakthecycle.ui.theme.ErrorColor
import kau.brave.breakthecycle.ui.theme.Main
import kau.brave.breakthecycle.ui.theme.White
import kau.brave.breakthecycle.utils.Constants.SIGNIN_ID_PASSWD_ROUTE

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
                .padding(bottom = 44.dp)
                .fillMaxSize()
                .shadow(5.dp, RoundedCornerShape(bottomEnd = 70.dp))
                .clip(RoundedCornerShape(bottomEnd = 70.dp))
                .background(White),
        ) {
            Column(
                modifier = Modifier
                    .statusBarsPadding()
                    .fillMaxSize()
                    .background(White)
                    .padding(horizontal = 28.dp)
            ) {

                BackIcon {
                    appstate.navController.popBackStack()
                }
                HeightSpacer(dp = 45.dp)

                Text(text = "전화번호 인증을\n진행해 주세요.", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                HeightSpacer(dp = 60.dp)

                Text(text = "전화번호", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    CustomTextField(
                        modifier = Modifier.weight(5f),
                        value = phone,
                        maxSize = 11,
                        placeholderText = "전화번호를 입력해주세요.",
                        onvalueChanged = { phone = it }
                    )

                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .weight(3f),
                        contentPadding = PaddingValues(vertical = 0.dp, horizontal = 3.dp),
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = if (phone.length == 11) Main else Disabled),
                    ) {
                        Text(
                            "인증 번호 요청",
                            color = White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        )
                    }
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
                        maxSize = 6,
                        placeholderText = "인증번호를 입력해주세요.",
                        onvalueChanged = { phone = it }
                    )
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .weight(3f),
                        contentPadding = PaddingValues(vertical = 0.dp, horizontal = 3.dp),
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = if (phone.length == 6) Main else Disabled),
                    ) {
                        Text(
                            "인증",
                            color = White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
                Text(text = "03:00", color = ErrorColor, fontSize = 14.sp)

                SignInGraphBottomConfirmButton(
                    enabled = true,
                    onClick = {
                        appstate.navController.navigate(SIGNIN_ID_PASSWD_ROUTE)
                    }
                )
            }
        }
    }
}
