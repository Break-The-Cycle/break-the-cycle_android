package kau.brave.breakthecycle.ui.home.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kau.brave.breakthecycle.ui.model.DurationInfo

@Composable
fun HomeLegend() {

    val density = LocalDensity.current
    val legends = listOf(
        DurationInfo("생리 기간", Color(0xFFFE91B0)),
//        DurationInfo("평일", Color(0xFFF2F1F3)),
        DurationInfo("가임기", Color(0xFF8342EB)),
    )

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally)
    ) {
        items(legends) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Canvas(
                    modifier = Modifier.size(20.dp),
                ) {
                    drawCircle(
                        color = it.color,
                        radius = with(density) { 10.dp.toPx() },
                    )
                }
                Text(
                    text = it.title,
                    fontSize = 15.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}