package kau.brave.breakthecycle.data.response

import kau.brave.breakthecycle.domain.domain.DateParser
import kau.brave.breakthecycle.domain.model.BraveDate
import kau.brave.breakthecycle.utils.Constants

class MenstruationInfoResponse : ArrayList<MensturationInfoResponseItem>() {

    fun divisionToBraveDate(
        dateParser: DateParser
    ): List<List<BraveDate>> {
        val menstruationDays = mutableListOf<BraveDate>()
        val childBearingDays = mutableListOf<BraveDate>()
        val ovulationDays = mutableListOf<BraveDate>()
        this.forEach {
            when (it.division) {
                Constants.EXPECTED_MENSTRUATION, Constants.REAL_MENSTRUATION -> {
                    menstruationDays.addAll(
                        dateParser.parseDateRange(
                            it.startDate,
                            it.endDate
                        )
                    )
                }
                Constants.EXPECTED_OVULATION -> {
                    ovulationDays.add(dateParser.parseDate(it.startDate))
                }
                Constants.EXPECTED_CHILDBEARING_PERIOD -> {
                    childBearingDays.addAll(
                        dateParser.parseDateRange(
                            it.startDate,
                            it.endDate
                        )
                    )
                }
            }
        }
        return listOf(menstruationDays, childBearingDays, ovulationDays)
    }
}
