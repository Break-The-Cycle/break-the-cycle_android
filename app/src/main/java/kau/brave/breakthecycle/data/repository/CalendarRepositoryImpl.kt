package kau.brave.breakthecycle.data.repository

import kau.brave.breakthecycle.data.response.MensturationInfoResponse
import kau.brave.breakthecycle.domain.repository.CalendarRepository
import kau.brave.breakthecycle.network.model.apiFlow
import kau.brave.breakthecycle.network.service.BraveClient
import kau.brave.breakthecycle.utils.ApiWrapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CalendarRepositoryImpl @Inject constructor(
    private val braveClient: BraveClient
) : CalendarRepository {

    override fun getMensturation(
        usePersonId: Int,
        startDate: String,
        endDate: String
    ): Flow<ApiWrapper<MensturationInfoResponse>> = apiFlow {
        braveClient.getMenstruation(usePersonId, startDate, endDate)
    }
}