package kau.brave.breakthecycle.ui.calendar.viewmodel

import android.util.Log
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kau.brave.breakthecycle.domain.domain.DateParser
import kau.brave.breakthecycle.domain.model.BraveDate
import kau.brave.breakthecycle.domain.repository.CalendarRepository
import kau.brave.breakthecycle.network.ServiceInterceptor
import kau.brave.breakthecycle.utils.Constants.EXPECTED_CHILDBEARING_PERIOD
import kau.brave.breakthecycle.utils.Constants.EXPECTED_MENSTRUATION
import kau.brave.breakthecycle.utils.Constants.EXPECTED_OVULATION
import kau.brave.breakthecycle.utils.Constants.REAL_MENSTRUATION
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@Stable
data class CalendarScreenUiState(
    val selectedDay: BraveDate = BraveDate(1900, 1, 1),
    val menstruationDays: List<BraveDate> = emptyList(),
    val childBearingDays: List<BraveDate> = emptyList(),
)

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val calendarRepository: CalendarRepository,
    private val dateParser: DateParser
) : ViewModel() {

    private val _selectedDay = MutableStateFlow(
        Calendar.getInstance().let { calendar ->
            BraveDate(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH)
            )
        }
    )
    private val _menstruationDays = MutableStateFlow(emptyList<BraveDate>())
    private val _childBearingDays = MutableStateFlow(emptyList<BraveDate>())

    val uiState: StateFlow<CalendarScreenUiState> = combine(
        _selectedDay, _menstruationDays, _childBearingDays
    ) { selectedDay, menstruationDays, childBearingDays ->
        CalendarScreenUiState(
            selectedDay = selectedDay,
            menstruationDays = menstruationDays,
            childBearingDays = childBearingDays
        )
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), CalendarScreenUiState()
    )

    fun updateMensturationDay(braveDate: BraveDate) {
        _selectedDay.value = braveDate
    }

    fun updateRange(startDate: BraveDate, endDate: BraveDate) = viewModelScope.launch {
        calendarRepository.getMensturation(
            usePersonId = ServiceInterceptor.usePersonId,
            startDate = startDate.format(),
            endDate = endDate.format()
        ).collectLatest { apiState ->
            apiState.onSuccess { response ->
                val menstruation = response.data ?: return@onSuccess
                val menstruationDays = mutableListOf<BraveDate>()
                val childBearingDays = mutableListOf<BraveDate>()
                menstruation.forEach {
                    when (it.division) {
                        EXPECTED_MENSTRUATION, REAL_MENSTRUATION -> {
                            menstruationDays.addAll(
                                dateParser.parseDateRange(
                                    it.startDate,
                                    it.endDate
                                )
                            )
                        }
                        EXPECTED_OVULATION -> {

                        }
                        EXPECTED_CHILDBEARING_PERIOD -> {
                            childBearingDays.addAll(
                                dateParser.parseDateRange(
                                    it.startDate,
                                    it.endDate
                                )
                            )
                        }
                    }
                }
                _menstruationDays.value = menstruationDays
                _childBearingDays.value = childBearingDays
            }
        }
    }
}