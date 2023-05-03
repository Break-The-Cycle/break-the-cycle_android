package kau.brave.breakthecycle.ui.diary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kau.brave.breakthecycle.theme.Gray300
import kau.brave.breakthecycle.ui.auth.signin.BackIcon

@Composable
fun DiaryWriteScreen() {

    val title by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Row() {
                BackIcon {

                }
                Text(text = "캘린더")
            }
            Text(text = "완료")
        }

        BasicTextField(value = "", onValueChange = {

        }, decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(0.dp, 10.dp)
            ) {
                Box(Modifier.weight(1f)) {
                    if (title.isEmpty()) {
                        Text(
                            "제목을 입력해주세요.",
                            style = LocalTextStyle.current.copy(
                                color = Color.Black.copy(alpha = 0.5f),
                                fontSize = 16.sp,
                            ),
                        )
                    }
                    innerTextField()
                }
            }
        })
        Text(text = "날짜 입력 | 글자 수 ")
        Divider(color = Gray300)

        BasicTextField(modifier = Modifier.weight(1f), value = "", onValueChange = {},
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(0.dp, 10.dp)
                ) {
                    Box(Modifier.weight(1f)) {
                        if (title.isEmpty()) {
                            Text(
                                "내용을 입력해주세요.",
                                style = LocalTextStyle.current.copy(
                                    color = Color.Black.copy(alpha = 0.5f),
                                    fontSize = 16.sp,
                                ),
                            )
                        }
                        innerTextField()
                    }
                }
            })

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Text(text = "사진을 입력해주세요.(1/5)")
            Row {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .background(Gray300)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.Center),
                        tint = Color.White
                    )
                }
            }
        }


    }

}