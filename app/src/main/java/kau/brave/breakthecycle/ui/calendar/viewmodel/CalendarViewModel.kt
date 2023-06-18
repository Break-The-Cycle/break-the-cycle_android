package kau.brave.breakthecycle.ui.calendar.viewmodel

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kau.brave.breakthecycle.RoseDaysApplication.Companion.isSecretMode
import kau.brave.breakthecycle.data.request.PasswordRequest
import kau.brave.breakthecycle.domain.domain.DateParser
import kau.brave.breakthecycle.domain.model.BraveDate
import kau.brave.breakthecycle.domain.model.BraveDiary
import kau.brave.breakthecycle.domain.usecase.CalendarUseCase
import kau.brave.breakthecycle.network.ServiceInterceptor
import kau.brave.breakthecycle.ui.model.DateType
import kau.brave.breakthecycle.utils.Constants.EXPECTED_CHILDBEARING_PERIOD
import kau.brave.breakthecycle.utils.Constants.EXPECTED_MENSTRUATION
import kau.brave.breakthecycle.utils.Constants.EXPECTED_OVULATION
import kau.brave.breakthecycle.utils.Constants.PREF_HASHED_PW
import kau.brave.breakthecycle.utils.Constants.REAL_MENSTRUATION
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@Stable
data class CalendarScreenUiState(
    val selectedDay: BraveDate = BraveDate(1900, 1, 1),
    val menstruationDays: List<BraveDate> = emptyList(),
    val childBearingDays: List<BraveDate> = emptyList(),
    val ovulationDays: List<BraveDate> = emptyList(),
    val selectedDateType: DateType = DateType.NORMAL
)

@Stable
data class SecretCalendarScreenUiState(
    val selectedDay: BraveDate = BraveDate(1900, 1, 1),
    val violentDays: List<BraveDate> = emptyList(),
    val violentDiary: BraveDiary = BraveDiary.default(),
)

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val calendarUseCase: CalendarUseCase,
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
    private val _selectedDateType = MutableStateFlow(DateType.NORMAL)
    private val _menstruationDays = MutableStateFlow(emptyList<BraveDate>())
    private val _childBearingDays = MutableStateFlow(emptyList<BraveDate>())
    private val _ovulationDays = MutableStateFlow(emptyList<BraveDate>())

    private val _violentDays = MutableStateFlow(emptyList<BraveDate>())
    private val _violentDiary = MutableStateFlow(BraveDiary.default())

    val uiState: StateFlow<CalendarScreenUiState> = combine(
        _selectedDay, _menstruationDays, _childBearingDays, _ovulationDays, _selectedDateType
    ) { selectedDay, menstruationDays, childBearingDays, ovulationDays, selectedDateType ->
        CalendarScreenUiState(
            selectedDay = selectedDay,
            menstruationDays = menstruationDays,
            childBearingDays = childBearingDays,
            ovulationDays = ovulationDays,
            selectedDateType = selectedDateType,
        )
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), CalendarScreenUiState()
    )

    val secretUiState: StateFlow<SecretCalendarScreenUiState> = combine(
        _selectedDay, _violentDays, _violentDiary
    ) { selectedDay, violentDays, violentDiary ->
        SecretCalendarScreenUiState(
            selectedDay = selectedDay,
            violentDays = violentDays,
            violentDiary = violentDiary
        )
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), SecretCalendarScreenUiState()
    )

    fun setSelectedDay(selectedDay: BraveDate) {
        _selectedDay.value = selectedDay
        updateSelectedDateType()
        if (isSecretMode.value) {
            getDiaryDetail()
        }
    }

    private fun getDiaryDetail() = viewModelScope.launch {
        val passwordSHA256 = async {
            calendarUseCase.getToken(PREF_HASHED_PW)
        }

        calendarUseCase.getViolentRecord(
            usePersonId = ServiceInterceptor.usePersonId,
            targetDate = _selectedDay.value.format(),
            passwordRequest = PasswordRequest(
                password = passwordSHA256.await().first()
            )
        ).collectLatest { apiState ->
            apiState.onSuccess { response ->
                val diaryDetail = response.data ?: return@onSuccess
                diaryDetail.forEach {
                    if (it.division == "DIARY") {
                        val diaryContents = it.diaryContents ?: return@forEach
                        _violentDiary.value = _violentDiary.value.copy(
                            title = diaryContents.title,
                            contents = diaryContents.contents,
                        )
                    } else if (it.division == "PICTURE") {
                        val image = it.image ?: return@forEach
                        _violentDiary.value = _violentDiary.value.copy(
                            images = _violentDiary.value.images.apply {
                                add(image)
                            }
                        )
                    }
                }
            }
        }
    }

    private fun getViolentRecordDates(startDate: BraveDate, endDate: BraveDate) =
        viewModelScope.launch {
            calendarUseCase.getViolentRecordDates(
                usePersonId = ServiceInterceptor.usePersonId,
                startDate = startDate.format(),
                endDate = endDate.format()
            ).collectLatest { apiState ->
                apiState.onSuccess { response ->
                    val violentRecordDates = response.data ?: return@onSuccess
                    val violentRecordDays = mutableListOf<BraveDate>()
                    violentRecordDates.forEach {
                        violentRecordDays.add(dateParser.parseDate(it))
                    }
                    _violentDays.value = violentRecordDays
                }
            }
        }

    fun updateRange(startDate: BraveDate, endDate: BraveDate) = viewModelScope.launch {
        getViolentRecordDates(startDate, endDate)
        getMenstruation(startDate, endDate)
    }

    private suspend fun getMenstruation(
        startDate: BraveDate,
        endDate: BraveDate
    ) {
        calendarUseCase.getMenstruation(
            usePersonId = ServiceInterceptor.usePersonId,
            startDate = startDate.format(),
            endDate = endDate.format()
        ).collectLatest { apiState ->
            apiState.onSuccess { response ->
                val menstruation = response.data ?: return@onSuccess
                val menstruationDays = mutableListOf<BraveDate>()
                val childBearingDays = mutableListOf<BraveDate>()
                val ovulationDays = mutableListOf<BraveDate>()
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
                            ovulationDays.add(dateParser.parseDate(it.startDate))
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
                _ovulationDays.value = ovulationDays
                updateSelectedDateType()
            }
        }
    }

    /** 선택한 날짜가 특정 어떤 날인지 구한다. */
    private fun updateSelectedDateType() {
        if (_menstruationDays.value.contains(_selectedDay.value)) {
            _selectedDateType.value = DateType.MENSTRUATION
        } else if (_ovulationDays.value.contains(_selectedDay.value)) {
            _selectedDateType.value = DateType.OVULATION
        } else if (_childBearingDays.value.contains(_selectedDay.value)) {
            _selectedDateType.value = DateType.CHILDBEARING
        } else {
            _selectedDateType.value = DateType.NORMAL
        }
    }
}