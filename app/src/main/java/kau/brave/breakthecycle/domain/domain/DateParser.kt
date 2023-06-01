package kau.brave.breakthecycle.domain.domain

import kau.brave.breakthecycle.domain.model.BraveDate

interface DateParser {

    fun parseDateRange(startDate: String, endDate: String): List<BraveDate>

}