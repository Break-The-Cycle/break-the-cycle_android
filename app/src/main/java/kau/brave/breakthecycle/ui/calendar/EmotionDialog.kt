package kau.brave.breakthecycle.ui.calendar

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kau.brave.breakthecycle.theme.White
import kau.brave.breakthecycle.ui.model.Emotions

@Composable
fun EmotionDilaog(
    dialogVisiblity: Boolean,
    updateDialogVisibility: (Boolean) -> Unit,
) {
    if (dialogVisiblity) {
        var selectedEmotion by remember {
            mutableStateOf(Emotions.NONE)
        }
        Dialog(onDismissRequest = { updateDialogVisibility(false) }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                shape = RoundedCornerShape(20.dp),
                elevation = 10.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "오늘의 기분을 입력해 보아요.",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Emotions.values().forEach {
                            Image(
                                painter = painterResource(id = if (selectedEmotion == it) it.coloredIcon else it.defaultIcon),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clickable {
                                        selectedEmotion = it
                                    },
                                contentScale = ContentScale.Fit
                            )
                        }
                    }
                    Button(onClick = {
                        updateDialogVisibility(false)
                    }) {
                        Text(
                            text = "완료",
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 0.dp),
                            fontSize = 18.sp,
                            color = White
                        )
                    }
                }
            }

        }
    }
}

