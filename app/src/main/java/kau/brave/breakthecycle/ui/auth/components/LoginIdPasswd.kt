package kau.brave.breakthecycle.ui.auth.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kau.brave.breakthecycle.ui.component.CustomTextField
import kau.brave.breakthecycle.ui.component.HeightSpacer
import kau.brave.breakthecycle.theme.Disabled
import kau.brave.breakthecycle.theme.Main
import kau.brave.breakthecycle.theme.White
import kau.brave.breakthecycle.utils.Constants

@Preview
@Composable
fun LoginIdPasswd(
    id: String = "",
    passwd: String = "",
    updateId: (String) -> Unit = {},
    updatePasswd: (String) -> Unit = {},
    navigateToMainGraph: () -> Unit = {},
) {
    Text(text = "안녕하세요.\n로즈 데이즈입니다.", fontSize = 18.sp, fontWeight = FontWeight.Bold)
    HeightSpacer(30.dp)
    Text(text = "아이디", fontSize = 16.sp)
    CustomTextField(
        modifier = Modifier.testTag("ID_TEXT_FIELD"),
        value = id,
        onvalueChanged = updateId,
        placeholderText = "아이디를 입력해주세요."
    )

    HeightSpacer(dp = 20.dp)
    Text(text = "비밀번호")
    CustomTextField(
        modifier = Modifier.testTag("PASSWD_TEXT_FIELD"),
        value = passwd,
        onvalueChanged = updatePasswd,
        placeholderText = "비밀번호를 입력해주세요.",
        visualTransformation = PasswordVisualTransformation()
    )

    HeightSpacer(dp = 35.dp)
    Button(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Main,
            contentColor = White
        ),
        onClick = navigateToMainGraph,
    ) {
        Text(text = "로그인", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = White)
    }
}