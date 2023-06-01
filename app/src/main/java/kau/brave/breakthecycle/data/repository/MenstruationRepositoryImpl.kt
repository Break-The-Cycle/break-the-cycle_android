package kau.brave.breakthecycle.data.repository

import kau.brave.breakthecycle.data.response.MenstruationInfoResponse
import kau.brave.breakthecycle.domain.repository.MenstruationRepository
import kau.brave.breakthecycle.network.model.apiFlow
import kau.brave.breakthecycle.network.service.BraveClient
import kau.brave.breakthecycle.utils.ApiWrapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MenstruationRepositoryImpl @Inject constructor(
    private val braveClient: BraveClient
) : MenstruationRepository {

    override fun getMenstruation(
        usePersonId: Int,
        startDate: String,
        endDate: String
    ): Flow<ApiWrapper<MenstruationInfoResponse>> = apiFlow {
        braveClient.getMenstruation(usePersonId, startDate, endDate)
    }
}