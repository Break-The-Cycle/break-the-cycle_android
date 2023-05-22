package kau.brave.breakthecycle.ui.auth.userinfo

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kau.brave.breakthecycle.domain.model.BraveDate
import kau.brave.breakthecycle.domain.repository.AuthRepository
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _duration = mutableStateOf(7)
    val duration: State<Int> get() = _duration

    private val _mensturationDay = mutableStateOf(
        Calendar.getInstance().let { calendar ->
            BraveDate(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH)
            )
        }
    )
    val mensturationDay: State<BraveDate> get() = _mensturationDay

    fun updateDuration(duration: Int) {
        _duration.value = duration
    }

    fun updateMensturationDay(braveDate: BraveDate) {
        _mensturationDay.value = braveDate
    }

    fun fetchUserInfo() = viewModelScope.launch {
        
        // TODO 서버에 유저 정보 저장
    }

}