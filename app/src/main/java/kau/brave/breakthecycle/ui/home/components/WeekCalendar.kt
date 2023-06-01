package kau.brave.breakthecycle.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kau.brave.breakthecycle.domain.model.BraveDate
import kau.brave.breakthecycle.theme.Main
import kau.brave.breakthecycle.ui.model.DayOfWeek


@Preview
@Composable
fun WeekCalendar(
    homeDays: List<BraveDate> = listOf(
        BraveDate(2021, 9, 11),
        BraveDate(2021, 9, 4),
        BraveDate(2021, 9, 5),
    ),
    menstruationDays: List<BraveDate> = listOf(BraveDate(2021, 9, 11)),
    childBearingDays: List<BraveDate> = emptyList(),
    ovulationDays: List<BraveDate> = emptyList()
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .padding(horizontal = 24.dp)
            .border(1.dp, Color(0xFFDBDBDB), RoundedCornerShape(20.dp))
            .shadow(15.dp, RoundedCornerShape(20.dp), clip = true, spotColor = Main)
            .background(Color.White)
            .padding(vertical = 14.dp, horizontal = 19.dp)
    ) {
        homeDays.forEach {
            Column(
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    text = DayOfWeek.getDayOfWeekFromDate(it).dayName,
                    color = Color(0xFF818181),
                    fontSize = 12.sp
                )
                Box(
                    modifier = Modifier
                        .padding(3.dp)
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(
                            it.getBackgroundColor(
                                menstruationDays = menstruationDays,
                                childBearingDays = childBearingDays,
                                ovulationsDays = ovulationDays
                            )
                        )
                ) {
                    Text(
                        text = it.day.toString(),
                        fontSize = 16.sp,
                        modifier = Modifier
                            .align(Alignment.Center),
                        color = it.getTextColor(
                            menstruationDays = menstruationDays,
                            childBearingDays = childBearingDays,
                            ovulationsDays = ovulationDays,
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}