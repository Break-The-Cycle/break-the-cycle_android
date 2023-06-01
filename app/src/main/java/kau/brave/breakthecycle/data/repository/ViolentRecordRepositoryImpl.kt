package kau.brave.breakthecycle.data.repository

import kau.brave.breakthecycle.data.request.PasswordRequest
import kau.brave.breakthecycle.data.response.DiaryDetailResponse
import kau.brave.breakthecycle.domain.repository.ViolentRecordRepository
import kau.brave.breakthecycle.network.model.apiFlow
import kau.brave.breakthecycle.network.service.BraveClient
import kau.brave.breakthecycle.utils.ApiWrapper
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class ViolentRecordRepositoryImpl @Inject constructor(
    private val braveClient: BraveClient
) : ViolentRecordRepository {

    override fun getViolentRecordDates(
        usePersonId: Int,
        startDate: String,
        endDate: String
    ): Flow<ApiWrapper<List<String>>> = apiFlow {
        braveClient.getViolentRecordDates(
            usePersonId = usePersonId,
            startDate = startDate,
            endDate = endDate
        )
    }

    override fun getViolentRecord(
        usePersonId: Int,
        targetDate: String,
        passwordRequest: PasswordRequest
    ): Flow<ApiWrapper<DiaryDetailResponse>> = apiFlow {
        braveClient.getViolentRecord(
            usePersonId = usePersonId,
            targetDate = targetDate,
            passwordRequest = passwordRequest
        )
    }

    override fun uploadViolentRecord(
        diaryContents: HashMap<String, RequestBody>,
        pictureList: List<MultipartBody.Part>
    ): Flow<ApiWrapper<String>> = apiFlow {
        braveClient.uploadViolentRecord(
            diaryContents = diaryContents,
            pictureList = pictureList
        )
    }


}