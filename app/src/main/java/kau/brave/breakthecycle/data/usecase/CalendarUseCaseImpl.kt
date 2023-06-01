package kau.brave.breakthecycle.data.usecase

import kau.brave.breakthecycle.data.request.PasswordRequest
import kau.brave.breakthecycle.data.response.DiaryDetailResponse
import kau.brave.breakthecycle.data.response.MenstruationInfoResponse
import kau.brave.breakthecycle.domain.repository.MenstruationRepository
import kau.brave.breakthecycle.domain.repository.ViolentRecordRepository
import kau.brave.breakthecycle.domain.usecase.CalendarUseCase
import kau.brave.breakthecycle.utils.ApiWrapper
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class CalendarUseCaseImpl @Inject constructor(
    private val menstruationRepository: MenstruationRepository,
    private val violentRecordRepository: ViolentRecordRepository
) : CalendarUseCase {
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

    override fun getViolentRecord(
        usePersonId: Int,
        targetDate: String,
        passwordRequest: PasswordRequest
    ): Flow<ApiWrapper<DiaryDetailResponse>> {
        return violentRecordRepository.getViolentRecord(
            usePersonId = usePersonId,
            targetDate = targetDate,
            passwordRequest = passwordRequest
        )
    }

    override fun getViolentRecordDates(
        usePersonId: Int,
        startDate: String,
        endDate: String
    ): Flow<ApiWrapper<List<String>>> {
        return violentRecordRepository.getViolentRecordDates(
            usePersonId = usePersonId,
            startDate = startDate,
            endDate = endDate
        )
    }

    override fun uploadViolentRecord(
        diaryContents: HashMap<String, RequestBody>,
        pictureList: List<MultipartBody.Part>
    ): Flow<ApiWrapper<String>> {
        return violentRecordRepository.uploadViolentRecord(
            diaryContents = diaryContents,
            pictureList = pictureList
        )
    }
}