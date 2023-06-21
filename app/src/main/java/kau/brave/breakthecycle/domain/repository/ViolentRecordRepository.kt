package kau.brave.breakthecycle.domain.repository

import androidx.datastore.preferences.core.Preferences
import kau.brave.breakthecycle.data.request.ExportViolentRequest
import kau.brave.breakthecycle.data.request.PasswordRequest
import kau.brave.breakthecycle.data.response.DiaryDetailResponse
import kau.brave.breakthecycle.data.response.ExportViolentResponse
import kau.brave.breakthecycle.utils.ApiWrapper
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface ViolentRecordRepository {

    fun getViolentRecord(
        usePersonId: Int,
        targetDate: String,
        passwordRequest: PasswordRequest
    ): Flow<ApiWrapper<DiaryDetailResponse>>

    fun getViolentRecordDates(
        usePersonId: Int,
        startDate: String,
        endDate: String
    ): Flow<ApiWrapper<List<String>>>

    fun uploadViolentRecord(
        userPersonId: Int,
        diaryContents: HashMap<String, RequestBody>,
        pictureList: List<MultipartBody.Part>
    ): Flow<ApiWrapper<String>>

    fun exportViolentToken(
        usePersonId: Int,
        exportViolentRequest: ExportViolentRequest
    ): Flow<ApiWrapper<ExportViolentResponse>>
}