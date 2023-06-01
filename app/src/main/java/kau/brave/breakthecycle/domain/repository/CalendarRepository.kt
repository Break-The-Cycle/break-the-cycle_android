package kau.brave.breakthecycle.domain.repository

import kau.brave.breakthecycle.data.response.MensturationInfoResponse
import kau.brave.breakthecycle.utils.ApiWrapper
import kotlinx.coroutines.flow.Flow

interface CalendarRepository {

    fun getMensturation(
        usePersonId: Int,
        startDate: String,
        endDate: String
    ): Flow<ApiWrapper<MensturationInfoResponse>>
}