package kau.brave.breakthecycle.ui.home.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kau.brave.breakthecycle.R
import kau.brave.breakthecycle.RoseDaysApplication
import kau.brave.breakthecycle.theme.ReportColor
import kau.brave.breakthecycle.ui.home.HomeGraphItem

@Composable
fun HomeCircleGraph(
    homeGraphItems: List<HomeGraphItem>,
    homeMainText: String,
    homeSubText: String,
) {

    val density = LocalDensity.current

    Box(modifier = Modifier.padding(top = 20.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(64.dp)
                .aspectRatio(1f)
        ) {
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {

                homeGraphItems.forEach { homeGraphItem ->
                    drawArc(
                        brush = Brush.verticalGradient(
                            colors = homeGraphItem.color
                        ),
                        startAngle = homeGraphItem.startAngle,
                        sweepAngle = homeGraphItem.sweepAngle,
                        useCenter = false,
                        style = Stroke(
                            width = with(density) { homeGraphItem.width.toPx() },
                            cap = StrokeCap.Round
                        ),
                    )
                }
            }
        }

        if (RoseDaysApplication.isSecretMode.value) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .fillMaxHeight(0.7f)
                    .align(Alignment.Center)
                    .clip(CircleShape)
                    .clickable {

                    }
                    .background(ReportColor)
            ) {
                Text(
                    text = "신고하기", color = Color.White, fontSize = 34.sp,
                    modifier = Modifier.align(Alignment.Center),
                    fontWeight = FontWeight.Bold,
                )
            }
        } else {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = homeMainText,
                    fontSize = 24.sp,
                    color = Color.Black,
                )

                if (homeSubText.isNotEmpty()) {
                    Text(
                        text = homeSubText,
                        fontSize = 50.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = "TODAY",
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Image(
                painter = painterResource(id = R.drawable.ic_location_brave_24),
                contentDescription = "IC_LOCATION_BRAVE_24",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(24.dp, 34.dp)
            )
        }
    }
}