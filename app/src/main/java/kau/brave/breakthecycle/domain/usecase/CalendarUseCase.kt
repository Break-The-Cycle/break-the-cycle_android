package kau.brave.breakthecycle.domain.usecase

import androidx.datastore.preferences.core.Preferences
import kau.brave.breakthecycle.data.request.PasswordRequest
import kau.brave.breakthecycle.data.response.DiaryDetailResponse
import kau.brave.breakthecycle.data.response.MenstruationInfoResponse
import kau.brave.breakthecycle.utils.ApiWrapper
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface CalendarUseCase {

    fun getMenstruation(
        usePersonId: Int,
        startDate: String,
        endDate: String
    ): Flow<ApiWrapper<MenstruationInfoResponse>>

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

    fun getToken(type: Preferences.Key<String>): Flow<String>

}