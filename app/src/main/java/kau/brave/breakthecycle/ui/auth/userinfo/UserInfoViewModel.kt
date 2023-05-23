package kau.brave.breakthecycle.ui.auth.userinfo

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kau.brave.breakthecycle.data.request.OnBoardRequest
import kau.brave.breakthecycle.domain.model.BraveDate
import kau.brave.breakthecycle.domain.repository.AuthRepository
import kau.brave.breakthecycle.utils.Constants.AVERAGE_MENSTURATION_PEROID
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _duration = mutableStateOf(AVERAGE_MENSTURATION_PEROID)
    val period: State<Int> get() = _duration

    private val _mensturationStartDate = mutableStateOf(
        Calendar.getInstance().let { calendar ->
            BraveDate(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH)
            )
        }
    )
    private val _mensturationEndDate = mutableStateOf(
        Calendar.getInstance().let { calendar ->
            BraveDate(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH)
            )
        }
    )
    val mensturationStartDate: State<BraveDate> get() = _mensturationStartDate
    val mensturationEndDate: State<BraveDate> get() = _mensturationEndDate

    fun updateDuration(duration: Int) {
        _duration.value = duration
    }

    fun updateMensturationStartDay(braveDate: BraveDate) {
        _mensturationStartDate.value = braveDate
    }

    fun updateMensturationEndDate(braveDate: BraveDate) {
        _mensturationEndDate.value = braveDate
    }

    fun onboard(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) = viewModelScope.launch {
        authRepository.onboard(
            // TODO 유저 아이디 키로 설정하게끔 변경
            usePersonId = 123,
            onBoardRequest = OnBoardRequest(
                startDate = mensturationStartDate.value.format('-'),
                endDate = mensturationEndDate.value.format('-'),
                period = period.value
            )
        ).collectLatest { apiState ->
            apiState.onSuccess {
                onSuccess
            }
            apiState.onError(onError)
        }
    }


}