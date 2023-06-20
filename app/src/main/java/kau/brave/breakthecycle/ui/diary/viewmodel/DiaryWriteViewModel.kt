package kau.brave.breakthecycle.ui.diary.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kau.brave.breakthecycle.domain.repository.AuthRepository
import kau.brave.breakthecycle.domain.repository.ViolentRecordRepository
import kau.brave.breakthecycle.network.ServiceInterceptor
import kau.brave.breakthecycle.utils.Constants
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject


data class DiaryWriteUiState(
    val title: String = "",
    val contents: String = "",
    val photoUris: List<Uri> = emptyList(),
)

@HiltViewModel
class DiaryWriteViewModel @Inject constructor(
    private val violentRecordRepository: ViolentRecordRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _title = MutableStateFlow("")
    private val _contents = MutableStateFlow("")
    private val _photoUris = MutableStateFlow<List<Uri>>(emptyList())

    val uiState = combine(
        _title,
        _contents,
        _photoUris,
    ) { title, contents, photoUris ->
        DiaryWriteUiState(
            title = title,
            contents = contents,
            photoUris = photoUris,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DiaryWriteUiState())

    fun updateTitle(title: String) {
        _title.value = title
    }

    fun updateContents(contents: String) {
        _contents.value = contents
    }

    fun updatePhotoUris(photoUris: List<Uri>) {
        _photoUris.value = photoUris
    }

    fun addPhotoUri(photoUri: Uri?) {
        if (photoUri == null) return
        _photoUris.value = _photoUris.value + photoUri
    }


    /** MultipartBody으로 바꾸는 작업 */
    private fun String?.toPlainRequestBody() =
        requireNotNull(this).toRequestBody("text/plain".toMediaType())

    fun uploadDiary(
        context: Context,
        selectedDate: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) =
        viewModelScope.launch {
            val passwordSHA256 = async {
                authRepository.getToken(Constants.PREF_HASHED_PW)
            }
            val usePersonId = ServiceInterceptor.usePersonId
            val diaryContents = hashMapOf(
                "title" to _title.value.toPlainRequestBody(),
                "contents" to _contents.value.toPlainRequestBody(),
                "password" to passwordSHA256.await().first().toPlainRequestBody(),
                "reportDate" to selectedDate.toPlainRequestBody(),
            )

            val pictureList = _photoUris.value.map {
                val file = context.contentResolver.openInputStream(it)?.use { inputStream ->
                    val file = File(context.cacheDir, it.lastPathSegment)
                    file.outputStream().use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                    file
                } ?: throw IllegalStateException("이미지 파일을 찾지 못했습니다.")
                val requestBody: RequestBody = file.asRequestBody("image/*".toMediaType())
                MultipartBody.Part.createFormData("pictureList", file.name, requestBody)
            }

            violentRecordRepository.uploadViolentRecord(
                userPersonId = usePersonId,
                diaryContents = diaryContents,
                pictureList = pictureList
            ).collectLatest {
                it.onSuccess {
                    onSuccess()
                }
                it.onError(onError)
            }
        }

}