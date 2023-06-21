package kau.brave.breakthecycle.network.service

import kau.brave.breakthecycle.data.request.*
import kau.brave.breakthecycle.data.response.BraveResponse
import kau.brave.breakthecycle.data.response.ExportViolentResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import javax.inject.Inject

class BraveClient @Inject constructor(
    private val authService: AuthService,
    private val violentRecordService: ViolentRecordService
) {

    /** Auth */
    suspend fun login(loginRequest: LoginRequest) = authService.login(loginRequest = loginRequest)
    suspend fun validateAccessToken() = authService.validateAccessToken()
    suspend fun refreshToken(refreshToken: String) =
        authService.refreshToken(refreshToken = refreshToken)

    suspend fun dupIdCheck(loginId: String) =
        authService.dupIdCheck(loginId = loginId)

    suspend fun sendCertificationCode(phoneNumber: PhoneNumber) =
        authService.sendSmsCertificationCode(phoneNumber = phoneNumber)

    suspend fun confirmCetificationCode(phoneAndCertificationNumber: PhoneAndCertificationNumber) =
        authService.confirmCertificationCode(phoneAndCertificationNumber)

    suspend fun register(registerRequest: RegisterRequest) =
        authService.register(registerRequest = registerRequest)

    suspend fun onboard(usePersonId: Int, onBoardRequest: OnBoardRequest) =
        authService.onBoard(usePersonId = usePersonId, onBoardRequest = onBoardRequest)

    suspend fun getMenstruation(
        usePersonId: Int,
        startDate: String,
        endDate: String
    ) = authService.getMenstruation(usePersonId, startDate, endDate)


    /** 일기 관련 */
    suspend fun getViolentRecordDates(
        usePersonId: Int,
        startDate: String,
        endDate: String
    ) = violentRecordService.getViolentRecordDates(
        usePersonId = usePersonId,
        startDate = startDate,
        endDate = endDate
    )

    suspend fun uploadViolentRecord(
        userPersonId: Int,
        diaryContents: HashMap<String, RequestBody>,
        pictureList: List<MultipartBody.Part>
    ) = violentRecordService.uploadViolentRecord(userPersonId, diaryContents, pictureList)

    suspend fun getViolentRecord(
        usePersonId: Int,
        targetDate: String,
        passwordRequest: PasswordRequest
    ) = violentRecordService.getViolentRecord(usePersonId, targetDate, passwordRequest)

    suspend fun exportViolentToken(
        usePersonId: Int,
        exportViolentRequest: ExportViolentRequest
    ) = violentRecordService.exportViolentToken(usePersonId, exportViolentRequest)


}