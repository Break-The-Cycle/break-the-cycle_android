package kau.brave.breakthecycle.ui.calendar.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kau.brave.breakthecycle.utils.BraveDate
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(

) : ViewModel() {

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
    fun updateMensturationDay(braveDate: BraveDate) {
        _mensturationDay.value = braveDate
    }
}