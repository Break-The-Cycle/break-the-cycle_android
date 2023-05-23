package kau.brave.breakthecycle.network.service

import kau.brave.breakthecycle.data.response.BraveResponse
import kau.brave.breakthecycle.data.response.MensturationInfoResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ViolentRecordService {

    /** 폭력 일기 기록 범위만큼 불러오기(내용 미포함) */
    @GET("v1/violent-records/{usePersonId}/dates")
    fun getViolentRecordDates(
        @Path("usePersonId") usePersonId: Int,
        @Query("fromDate") startDate: String,
        @Query("toDate") endDate: String
    ): Response<BraveResponse<MensturationInfoResponse>>

    /** 폭력 기록/일기 업로드 */
    @Multipart
    @POST("v1/violent-records")
    suspend fun uploadViolentRecord(
        @PartMap diaryContents: HashMap<String, RequestBody>,
        @Part pictureList: List<MultipartBody.Part>
    ): Response<BraveResponse<String>>


    // TODO 수정필요
    /** 특정 날짜 폭력 일기 기록 불러오기(내용 포함) */
    @POST("v1/violent-records/{usePersonId}")
    suspend fun getViolentRecord(
        @Path("usePersonId") usePersonId: Int,
        @Body date: String
    ): Response<BraveResponse<String>>
}