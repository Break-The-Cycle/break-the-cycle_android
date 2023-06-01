package kau.brave.breakthecycle.domain.repository

import kau.brave.breakthecycle.data.response.MenstruationInfoResponse
import kau.brave.breakthecycle.utils.ApiWrapper
import kotlinx.coroutines.flow.Flow

interface MenstruationRepository {

    fun getMenstruation(
        usePersonId: Int,
        startDate: String,
        endDate: String
    ): Flow<ApiWrapper<MenstruationInfoResponse>>
}