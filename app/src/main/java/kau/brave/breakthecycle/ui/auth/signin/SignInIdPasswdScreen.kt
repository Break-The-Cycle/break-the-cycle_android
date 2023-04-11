package kau.brave.breakthecycle.ui.auth.signin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kau.brave.breakthecycle.domain.model.ApplicationState
import kau.brave.breakthecycle.ui.auth.components.SignInGraphBottomConfirmButton
import kau.brave.breakthecycle.ui.auth.model.VerificationStatus
import kau.brave.breakthecycle.ui.component.CustomTextField
import kau.brave.breakthecycle.ui.theme.*
import kau.brave.breakthecycle.utils.Constants.ONBOARD_GRAPH
import kau.brave.breakthecycle.utils.Constants.SIGNIN_GRAPH

@Composable
fun SignInIdPasswdScreen(appstate: ApplicationState) {

    var id by remember {
        mutableStateOf("")
    }

    var idStatus by remember {
        mutableStateOf(VerificationStatus.NONE)
    }

    var passwd by remember {
        mutableStateOf("")
    }

    var passwdConfirm by remember {
        mutableStateOf("")
    }

    var passwdStatus by remember {
        mutableStateOf(VerificationStatus.NONE)
    }

    var passwdConfirmStatus by remember {
        mutableStateOf(VerificationStatus.NONE)
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
                    .padding(horizontal = 28.dp)
            ) {
                BackIcon {
                    appstate.navController.popBackStack()
                }

                Text(
                    text = "회원가입을\n진행해 주세요.",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(top = 45.dp, bottom = 60.dp),
                    fontWeight = FontWeight.Bold
                )

                /** 아이디 입력 */
                Text(text = "아이디", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    CustomTextField(
                        modifier = Modifier.weight(5f),
                        value = id,
                        placeholderText = "아이디를 입력해주세요.",
                        onvalueChanged = {
                            id = it
                            idStatus = VerificationStatus.NONE
                        }
                    )
                    Button(
                        onClick = {
                            /*TODO*/
                            idStatus = VerificationStatus.COMPLETE
                        },
                        modifier = Modifier
                            .weight(3f),
                        contentPadding = PaddingValues(vertical = 0.dp, horizontal = 3.dp),
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Main), // ButtonDefaults.buttonColors(backgroundColor = if (id.length == 6) Main else Disabled),
                    ) {
                        Text(
                            "중복 체크",
                            color = White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
                when (idStatus) {
                    VerificationStatus.ERROR -> {
                        Text(
                            text = "이미 사용중인 아이디입니다.",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = ErrorColor
                        )
                    }
                    VerificationStatus.COMPLETE -> {
                        Text(
                            text = "사용 가능한 아이디입니다.",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = AbleColor
                        )
                    }
                    else -> {
                        Text(text = " ", fontSize = 12.sp)
                    }
                }

                /** 비밀번호 입력 */
                Text(
                    text = "비밀번호",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 15.dp)
                )
                CustomTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = passwd,
                    placeholderText = "비밀번호를 입력해주세요.",
                    onvalueChanged = {
                        passwd = it
                        passwdStatus =
                            if (it.isEmpty()) VerificationStatus.NONE else {
                                if (it.length in 8..12 && it.matches(Regex(".*\\d.*")) && it.matches(
                                        Regex(".*[a-z].*")
                                    ) && it.matches(Regex(".*[A-Z].*")) && it.matches(Regex(".*[!@#\$%^&*(),.?\":{}|<>].*"))
                                ) VerificationStatus.COMPLETE else VerificationStatus.ERROR
                            }
                    },
                    visualTransformation = PasswordVisualTransformation()
                )
                Text(
                    text = "8~12글자, 숫자, 대소문자, 특수문자 포함이여야 합니다.",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = when (passwdStatus) {
                        VerificationStatus.ERROR -> ErrorColor
                        VerificationStatus.COMPLETE -> AbleColor
                        else -> Disabled
                    },
                )

                /** 비밀번호 확인 */
                CustomTextField(
                    modifier = Modifier.fillMaxWidth().padding(top=10.dp),
                    value = passwdConfirm,
                    placeholderText = "비밀번호를 확인해주세요.",
                    onvalueChanged = {
                        passwdConfirm = it
                        passwdConfirmStatus =
                            if (it.isEmpty()) VerificationStatus.NONE else {
                                if (passwd == passwdConfirm) VerificationStatus.COMPLETE else VerificationStatus.ERROR
                            }
                    },
                    visualTransformation = PasswordVisualTransformation()
                )
                when (passwdConfirmStatus) {
                    VerificationStatus.ERROR -> {
                        Text(
                            text = "비밀번호가 일치하지 않습니다.",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = ErrorColor
                        )
                    }
                    VerificationStatus.COMPLETE -> {
                        Text(
                            text = "비밀번호가 일치합니다.",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = AbleColor
                        )
                    }
                    else -> {
                        Text(text = " ", fontSize = 12.sp)
                    }
                }

                SignInGraphBottomConfirmButton(
                    onClick = {
                        appstate.navController.navigate(ONBOARD_GRAPH) {
                            popUpTo(SIGNIN_GRAPH) {
                                inclusive = true
                            }
                        }
                    },
                    enabled = true //idStatus == VerificationStatus.COMPLETE && passwdStatus == VerificationStatus.COMPLETE && passwdConfirmStatus == VerificationStatus.COMPLETE
                )
            }
        }
    }
}
