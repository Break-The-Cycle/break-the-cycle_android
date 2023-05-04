package kau.brave.breakthecycle.ui.diary

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kau.brave.breakthecycle.R
import kau.brave.breakthecycle.theme.Gray300
import kau.brave.breakthecycle.theme.Gray800
import kau.brave.breakthecycle.theme.Main
import kau.brave.breakthecycle.ui.auth.components.BackIcon

@Preview
@Composable
fun DiaryWriteScreen() {

    val title by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.size(24.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                        contentDescription = "IC_ARROW_BACK"
                    )
                }
                Text(text = "캘린더", fontSize = 16.sp, color = Gray800)
            }
            Text(text = "완료", fontSize = 16.sp, color = Gray800, fontWeight = FontWeight.Bold)
        }

        BasicTextField(
            modifier = Modifier.padding(top = 20.dp),
            value = "",
            onValueChange = {

            },
            textStyle = LocalTextStyle.current.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            decorationBox = { innerTextField ->
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
                                    fontSize = 18.sp,
                                ),
                            )
                        }
                        innerTextField()
                    }
                }
            })

        Text(
            text = "날짜 입력 | 글자 수 ", fontSize = 14.sp,
            color = Gray300, modifier = Modifier.padding(top = 5.dp)
        )
        Divider(color = Gray300, modifier = Modifier.padding(0.dp, 10.dp))

        BasicTextField(modifier = Modifier.weight(1f),
            textStyle = LocalTextStyle.current.copy(
                fontSize = 16.sp,
                color = Color.Black,
            ),
            value = "",
            onValueChange = {},
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
                .height(200.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(text = "사진을 입력해주세요.(1/5)", fontSize = 18.sp, color = Gray800)

            Row {
                Box(
                    modifier = Modifier
                        .size(106.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Gray300)
                        .clickable { }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .align(Alignment.Center),
                        tint = Color.White
                    )
                }
            }
        }


    }

}