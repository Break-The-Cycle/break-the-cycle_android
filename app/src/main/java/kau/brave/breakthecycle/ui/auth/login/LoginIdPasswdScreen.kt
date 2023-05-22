package kau.brave.breakthecycle.ui.auth.login

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kau.brave.breakthecycle.R
import kau.brave.breakthecycle.theme.*
import kau.brave.breakthecycle.ui.model.ApplicationState
import kau.brave.breakthecycle.utils.rememberApplicationState
import kau.brave.breakthecycle.ui.auth.components.LoginIdPasswd
import kau.brave.breakthecycle.ui.component.HeightSpacer
import kau.brave.breakthecycle.ui.component.SocialLoginBtn
import kau.brave.breakthecycle.ui.component.WidthSpacerWithLine
import kau.brave.breakthecycle.utils.Constants
import kau.brave.breakthecycle.utils.Constants.MAIN_GRAPH
import kau.brave.breakthecycle.utils.Constants.SIGNIN_GRAPH


@Preview
@Composable
fun LoginIdPasswdScreen(appState: ApplicationState = rememberApplicationState()) {

    val viewModel: LoginViewModel = hiltViewModel()

    val login: () -> Unit = {
        viewModel.login(
            onSuccess = {
                appState.navController.navigate(MAIN_GRAPH) {
                    popUpTo(Constants.AUTH_GRAPH) {
                        inclusive = true
                    }
                }
            },
            onError = {
                appState.showSnackbar(it)
            }
        )
    }

    Box {
        Image(
            painter = painterResource(id = R.mipmap.img_login_background),
            contentDescription = "IMG_LOGIN_BACKGROUND",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 24.dp, top = 100.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(60.dp, 0.dp, 0.dp, 60.dp))
                    .background(White)
                    .padding(28.dp, 70.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                LoginIdPasswd(
                    id = viewModel.id.value,
                    passwd = viewModel.passwd.value,
                    updateId = viewModel.updateId,
                    updatePasswd = viewModel.updatePasswd,
                    login = login
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
                            appState.navigate(SIGNIN_GRAPH)
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
}


