package kau.brave.breakthecycle.ui.mypage

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kau.brave.breakthecycle.data.request.ExportViolentRequest
import kau.brave.breakthecycle.domain.repository.AuthRepository
import kau.brave.breakthecycle.domain.repository.ViolentRecordRepository
import kau.brave.breakthecycle.network.ServiceInterceptor
import kau.brave.breakthecycle.utils.Constants.PREF_HASHED_PW
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MypageViewModel @Inject constructor(
    private val violentRecordRepository: ViolentRecordRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _exportToken = mutableStateOf("Token 불러오는 중..")
    val exportToken: State<String> get() = _exportToken

    fun exportDiaryToken() = viewModelScope.launch {

        val password =
            withContext(Dispatchers.IO) { authRepository.getToken(PREF_HASHED_PW).first() }
        violentRecordRepository.exportViolentToken(
            usePersonId = ServiceInterceptor.usePersonId,
            exportViolentRequest = ExportViolentRequest(
                password = password,
                fromDate = "2021-01-01",
                toDate = "2025-12-31"
            )
        ).collectLatest { apiState ->
            apiState.onSuccess {
                val response = it.data ?: return@onSuccess
                _exportToken.value = response.submissionToken
            }
        }
    }
}