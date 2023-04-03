package kau.brave.breakthecycle.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview


val PASSWD_REGEX =
    Regex("""^(?=.*[A-Za-z])(?=.*\d)(?=.*[@${'$'}!%*#?&])[A-Za-z\d@${'$'}!%*#?&]{8,}${'$'}""")


@Composable
fun LoginIdPasswdScreen() {

    var id by remember { mutableStateOf("") }
    var passwd by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        LoginIdPasswd(
            id = id,
            passwd = passwd,
            updateId = { id = it },
            updatePasswd = { passwd = it }
        )

        Row {
            Text(text = "회원가입")
            Text(text = "비밀번호 찾기")
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "카카오 로그인")
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "네이버 로그인")
        }
    }
}

@Preview
@Composable
private fun LoginIdPasswd(
    id: String = "",
    passwd: String = "",
    updateId: (String) -> Unit = {},
    updatePasswd: (String) -> Unit = {},
) {
    Text(text = "안녕하세요.\n로즈데이즈입니다.")
    Text(text = "아이디")
    TextField(
        modifier = Modifier.testTag("ID_TEXT_FIELD"),
        value = id,
        onValueChange = updateId,
        label = { Text(text = "아이디") })
    Text(text = "비밀번호")
    TextField(
        modifier = Modifier.testTag("PASSWD_TEXT_FIELD"),
        value = passwd,
        onValueChange = updatePasswd,
        label = { Text(text = "비밀번호") })
    Button(
        onClick = { /*TODO*/ },
        enabled = id.length >= 7 && PASSWD_REGEX.matches(passwd)
    ) {
        Text(text = "로그인")
    }
}
