package kau.brave.breakthecycle.ui.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kau.brave.breakthecycle.R
import kau.brave.breakthecycle.domain.model.ApplicationState
import kau.brave.breakthecycle.domain.model.DayOfWeek
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
                .size(56.dp)
                .align(Alignment.CenterHorizontally)
        )
        WeekCalendar()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(35.dp)
                .aspectRatio(1f)
        ) {
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                drawArc(
                    color = Main,
                    startAngle = 0f,
                    sweepAngle = 110f,
                    useCenter = false,
                    style = Stroke(width = with(density) { 20.dp.toPx() }, cap = StrokeCap.Round),
                )
                drawArc(
                    color = Sub2,
                    startAngle = 100f,
                    sweepAngle = 50f,
                    useCenter = false,
                    style = Stroke(width = with(density) { 20.dp.toPx() }, cap = StrokeCap.Round),
                )
                drawArc(
                    color = Sub1,
                    startAngle = 140f,
                    sweepAngle = 100f,
                    useCenter = false,
                    style = Stroke(width = with(density) { 20.dp.toPx() }, cap = StrokeCap.Round),
                )
                drawArc(
                    color = Main,
                    startAngle = 230f,
                    sweepAngle = 110f,
                    useCenter = false,
                    style = Stroke(width = with(density) { 20.dp.toPx() }, cap = StrokeCap.Round),
                )
                drawArc(
                    color = Sub2,
                    startAngle = 330f,
                    sweepAngle = 30f,
                    useCenter = false,
                    style = Stroke(width = with(density) { 20.dp.toPx() }, cap = StrokeCap.Round),
                )
            }

            Text(
                text = "생리 0일차", modifier = Modifier.align(Alignment.Center),
                fontSize = 20.sp,
            )
        }
    }

}

@Composable
private fun WeekCalendar() {
    val calendar = Calendar.getInstance()
    Row(modifier = Modifier.fillMaxWidth()) {
        for (i in 0 until 7) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
                Text(text = DayOfWeek.find(dayOfWeek))
                Text(text = calendar.get(Calendar.DAY_OF_MONTH).toString())
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
    }
}