package kau.brave.breakthecycle.ui.home.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kau.brave.breakthecycle.R
import kau.brave.breakthecycle.domain.model.ApplicationState
import kau.brave.breakthecycle.domain.model.DayOfWeek
import kau.brave.breakthecycle.ui.theme.Gray100
import kau.brave.breakthecycle.ui.theme.Main
import kau.brave.breakthecycle.ui.theme.Sub1
import kau.brave.breakthecycle.ui.theme.Sub2
import java.util.*

@Composable
fun HomeScreen(appstate: ApplicationState) {

    val density = LocalDensity.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
    ) {

        Image(
            painter = painterResource(id = R.drawable.img_logo_small),
            contentDescription = "IMG_LOGO_SMALL",
            modifier = Modifier
                .padding(vertical = 20.dp)
                .size(56.dp)
                .align(Alignment.CenterHorizontally)
        )

        WeekCalendar()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(64.dp)
                .aspectRatio(1f)
        ) {
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                drawArc(
                    color = Gray100,
                    startAngle = 100f,
                    sweepAngle = 50f,
                    useCenter = false,
                    style = Stroke(width = with(density) { 20.dp.toPx() }, cap = StrokeCap.Round),
                )
                drawArc(
                    color = Gray100,
                    startAngle = 230f,
                    sweepAngle = 180f,
                    useCenter = false,
                    style = Stroke(width = with(density) { 20.dp.toPx() }, cap = StrokeCap.Round),
                )
                drawArc(
                    brush = Brush.verticalGradient(
                        colors = listOf(Main, Main, Color(0xFFFFC2C2)),
                    ),
                    startAngle = 0f,
                    sweepAngle = 110f,
                    useCenter = false,
                    style = Stroke(width = with(density) { 30.dp.toPx() }, cap = StrokeCap.Round),
                )
                drawArc(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFE742EB), Color(0xFF3D70F1)),
                    ),
                    startAngle = 140f,
                    sweepAngle = 100f,
                    useCenter = false,
                    style = Stroke(width = with(density) { 30.dp.toPx() }, cap = StrokeCap.Round),
                )
            }

            Column(
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(
                    text = "생리까지",
                    fontSize = 24.sp,
                    color = Color.Black,
                )
                Text(
                    text = "N일",
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
            }

        }
    }
}

@Composable
private fun WeekCalendar() {
    val calendar = Calendar.getInstance()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .border(1.dp, Color(0xFFDBDBDB), RoundedCornerShape(20.dp))
            .shadow(15.dp, RoundedCornerShape(20.dp), clip = true, spotColor = Main)
            .background(Color.White)
            .padding(vertical = 14.dp, horizontal = 19.dp)
    ) {
        for (i in 0 until 7) {
            Column(
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
                Text(
                    text = DayOfWeek.find(dayOfWeek),
                    color = Color(0xFF818181),
                    fontSize = 12.sp
                )
                Text(
                    text = calendar.get(Calendar.DAY_OF_MONTH).toString(),
                    fontSize = 16.sp,
                    color = Color(0xFF3B3866)
                )
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
    }
}