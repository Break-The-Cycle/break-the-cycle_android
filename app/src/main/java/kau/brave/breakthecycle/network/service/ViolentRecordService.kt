package kau.brave.breakthecycle.network.service

import kau.brave.breakthecycle.data.request.PasswordRequest
import kau.brave.breakthecycle.data.request.RecordsOutRequest
import kau.brave.breakthecycle.data.response.BraveResponse
import kau.brave.breakthecycle.data.response.DiaryDetailResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ViolentRecordService {

    /** 폭력 일기 기록 범위만큼 불러오기(내용 미포함) */
    @GET("v1/violent-records/{usePersonId}/dates")
    suspend fun getViolentRecordDates(
        @Path("usePersonId") usePersonId: Int,
        @Query("fromDate") startDate: String,
        @Query("toDate") endDate: String
    ): Response<BraveResponse<List<String>>>

    /** 폭력 기록/일기 업로드 */
    @Multipart
    @POST("v1/violent-records/{usePersonId}")
    suspend fun uploadViolentRecord(
        @Path("usePersonId") usePersonId: Int,
        @PartMap diaryContents: HashMap<String, RequestBody>,
        @Part pictureList: List<MultipartBody.Part>
    ): Response<BraveResponse<String>>


    /** 특정 날짜 폭력 일기 기록 불러오기(내용 포함) */
    @POST("v1/violent-records/{usePersonId}/contents")
    suspend fun getViolentRecord(
        @Path("usePersonId") usePersonId: Int,
        @Query("targetDate") targetDate: String,
        @Body passwordRequest: PasswordRequest
    ): Response<BraveResponse<DiaryDetailResponse>>

    /** 폭력 일기 내보내기 */
    @POST("v1/violent-records/out")
    suspend fun outViolentRecord(
        @Body recordsOutRequest: RecordsOutRequest
    ): Response<BraveResponse<DiaryDetailResponse>>

}