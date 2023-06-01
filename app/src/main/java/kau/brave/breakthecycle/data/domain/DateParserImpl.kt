package kau.brave.breakthecycle.data.domain

import kau.brave.breakthecycle.domain.domain.DateParser
import kau.brave.breakthecycle.domain.model.BraveDate
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DateParserImpl @Inject constructor() : DateParser {

    override fun parseDateRange(startDate: String, endDate: String): List<BraveDate> {
        val dateList = mutableListOf<BraveDate>()
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val start = formatter.parse(startDate)
        val end = formatter.parse(endDate)

        val calendar = Calendar.getInstance()
        calendar.time = start

        while (!calendar.time.after(end)) {
            val formattedDate =
                SimpleDateFormat("yyyy,MM,dd", Locale.getDefault()).format(calendar.time).split(",")
            dateList.add(
                BraveDate(
                    year = formattedDate[0].toInt(),
                    month = formattedDate[1].toInt(),
                    day = formattedDate[2].toInt(),
                )
            )
            calendar.add(Calendar.DATE, 1)
        }

        return dateList

    }

    override fun parseDate(startDate: String): BraveDate {
        return try {
            val date = startDate.split("-").map { it.toInt() }
            BraveDate(
                year = date[0],
                month = date[1],
                day = date[2]
            )
        } catch (e: Exception) {
            throw IllegalArgumentException("${startDate}가 잘못된 날짜 형식입니다.")
        }
    }
}