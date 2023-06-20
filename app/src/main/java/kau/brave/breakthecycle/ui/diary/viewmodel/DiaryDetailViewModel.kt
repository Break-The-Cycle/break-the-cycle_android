package kau.brave.breakthecycle.ui.diary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kau.brave.breakthecycle.data.request.PasswordRequest
import kau.brave.breakthecycle.domain.model.BraveDiary
import kau.brave.breakthecycle.domain.repository.AuthRepository
import kau.brave.breakthecycle.domain.repository.ViolentRecordRepository
import kau.brave.breakthecycle.network.ServiceInterceptor
import kau.brave.breakthecycle.utils.Constants.PREF_HASHED_PW
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DiaryDetailUiState(
    val title: String = "",
    val contents: String = "",
    val reportDate: String = "",
    val photoUris: List<String> = emptyList(),
)

@HiltViewModel
class DiaryDetailViewModel @Inject constructor(
    private val diaryRepository: ViolentRecordRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _title = MutableStateFlow("")
    private val _contents = MutableStateFlow("")
    private val _photoUris = MutableStateFlow<List<String>>(emptyList())
    private val _reportDate = MutableStateFlow("")
    val uiState = combine(
        _title, _contents, _photoUris, _reportDate
    ) { title, contents, photoUris, reportDate ->
        DiaryDetailUiState(
            title = title,
            contents = contents,
            photoUris = photoUris,
            reportDate = reportDate
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DiaryDetailUiState())

    fun getDiaryDetail(targetDate: String) = viewModelScope.launch {
        val usePersonId = ServiceInterceptor.usePersonId
        val password = authRepository.getToken(PREF_HASHED_PW).first()
        diaryRepository.getViolentRecord(
            usePersonId = usePersonId,
            targetDate = targetDate,
            passwordRequest = PasswordRequest(password)
        ).collectLatest { apiState ->
            apiState.onSuccess { response ->
                val diaryDetail = response.data ?: return@onSuccess
                diaryDetail.forEach {
                    if (it.division == "DIARY") {
                        val diaryContents = it.diary ?: return@forEach
                        _title.value = diaryContents.title
                        _contents.value = diaryContents.contents
                        _reportDate.value = it.reportDate
                    } else if (it.division == "PICTURE") {
                        val image = it.image ?: return@forEach
                        _photoUris.update {
                            _photoUris.value.toMutableList().apply {
                                add(image)
                            }.toList()
                        }
                    }
                }
            }
        }
    }
}