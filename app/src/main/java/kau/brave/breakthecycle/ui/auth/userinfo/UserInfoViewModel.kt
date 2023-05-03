package kau.brave.breakthecycle.ui.auth.userinfo

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.util.*

class UserInfoViewModel : ViewModel() {

    private val _duration = mutableStateOf(7)
    val duration: State<Int> get() = _duration

    private val _mensturationDay = mutableStateOf(
        Calendar.getInstance().let { calendar ->
            Triple(
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

    fun fetchUserInfo() {
        // TODO 서버에 유저 정보 저장
    }

}