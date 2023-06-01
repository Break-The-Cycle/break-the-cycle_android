package kau.brave.breakthecycle.data.usecase

import kau.brave.breakthecycle.data.response.MenstruationInfoResponse
import kau.brave.breakthecycle.domain.repository.MenstruationRepository
import kau.brave.breakthecycle.domain.usecase.HomeUseCase
import kau.brave.breakthecycle.utils.ApiWrapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeUseCaseImpl @Inject constructor(
    private val menstruationRepository: MenstruationRepository
) : HomeUseCase {
    override fun getMenstruation(
        usePersonId: Int,
        startDate: String,
        endDate: String
    ): Flow<ApiWrapper<MenstruationInfoResponse>> {
        return menstruationRepository.getMenstruation(
            usePersonId = usePersonId,
            startDate = startDate,
            endDate = endDate
        )
    }

}