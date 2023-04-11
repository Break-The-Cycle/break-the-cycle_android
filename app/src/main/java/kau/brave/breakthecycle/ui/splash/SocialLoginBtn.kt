package kau.brave.breakthecycle.ui.splash

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kau.brave.breakthecycle.ui.theme.White

@Composable
fun SocialLoginBtn(
    color: Color,
    text: String,
    @DrawableRes image: Int,
    onClick: () -> Unit,
) {
    Button(
        modifier = Modifier
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(backgroundColor = color),
        shape = RoundedCornerShape(5.dp),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Image(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(19.dp),
                painter = painterResource(id = image),
                contentDescription = "IMG_KAKAO_BTN"
            )
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = text,
                color = White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
