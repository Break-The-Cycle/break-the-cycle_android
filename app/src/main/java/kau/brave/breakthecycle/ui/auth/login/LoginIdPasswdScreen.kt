package kau.brave.breakthecycle.ui.auth.login

import android.graphics.ColorMatrix
import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kau.brave.breakthecycle.R
import kau.brave.breakthecycle.domain.model.ApplicationState
import kau.brave.breakthecycle.domain.rememberApplicationState
import kau.brave.breakthecycle.ui.auth.components.LoginIdPasswd
import kau.brave.breakthecycle.ui.component.CustomTextField
import kau.brave.breakthecycle.ui.component.HeightSpacer
import kau.brave.breakthecycle.ui.splash.SocialLoginBtn
import kau.brave.breakthecycle.ui.theme.*
import kau.brave.breakthecycle.utils.Constants.PASSWD_REGEX


@Preview
@Composable
fun LoginIdPasswdScreen(applicationState: ApplicationState = rememberApplicationState()) {

    val viewModel: LoginViewModel = hiltViewModel()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Main)
            .padding(start = 24.dp, top = 100.dp)
    ) {
        Column(
            modifier = Modifier
                .offset(y = 9.dp)
                .fillMaxSize()
                .clip(RoundedCornerShape(60.dp, 0.dp, 0.dp, 60.dp))
                .background(White)
                .padding(28.dp, 70.dp)
                .verticalScroll(rememberScrollState())
        ) {
            LoginIdPasswd(
                id = viewModel.id.value,
                passwd = viewModel.passwd.value,
                updateId = viewModel::updateId,
                updatePasswd = viewModel::updatePasswd
            )

            HeightSpacer(20.dp)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "회원가입",
                    fontSize = 12.sp,
                    color = Sub2,
                    modifier = Modifier.clickable {
                        // TODO 회원가입 진행
                        // appstate.navigate(Constants.SIGNIN_GENDER_ROUTE)
                    },
                    textDecoration = TextDecoration.Underline
                )
                Text(text = "OR", fontSize = 12.sp, color = Gray600)
                Text(text = "비밀번호를 잊으셨나요?", fontSize = 12.sp, color = Gray600)
            }

            HeightSpacer(40.dp)
            WidthSpacerWithLine(color = Gray600)
            HeightSpacer(30.dp)

            SocialLoginBtn(
                color = KakaoColor,
                text = "카카오로 계속하기",
                image = R.mipmap.img_kakao,
                onClick = { /*TODO*/ }
            )
            SocialLoginBtn(
                color = NaverColor,
                text = "네이버로 계속하기",
                image = R.mipmap.img_naver,
                onClick = { /*TODO*/ }
            )
        }
    }
}


