package kau.brave.breakthecycle.ui.home

import android.telephony.SmsManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kau.brave.breakthecycle.data.database.AddressRepository
import kau.brave.breakthecycle.domain.domain.DateParser
import kau.brave.breakthecycle.domain.model.BraveDate
import kau.brave.breakthecycle.domain.usecase.HomeUseCase
import kau.brave.breakthecycle.theme.Gray100
import kau.brave.breakthecycle.theme.Main
import kau.brave.breakthecycle.ui.model.DateType
import kau.brave.breakthecycle.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject


data class HomeUiState(
    val homeDays: List<BraveDate> = emptyList(),
    val menstruationDays: List<BraveDate> = emptyList(),
    val childBearingDays: List<BraveDate> = emptyList(),
    val ovulationDays: List<BraveDate> = emptyList(),
    val homeGraphItems: List<HomeGraphItem> = emptyList(),
    val homeMainText: String = "",
    val homeSubText: String = "",
)


data class HomeGraphItem(
    val date: BraveDate,
    val dateType: DateType,
    val color: List<Color> = listOf(Gray100, Gray100),
    val width: Dp = 0.dp,
    val startAngle: Float = 0f,
    val sweepAngle: Float = 0f,
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCase: HomeUseCase,
    private val dateParser: DateParser,
    private val addressRepository: AddressRepository,
) : ViewModel() {

    private val _homeDays = MutableStateFlow(emptyList<BraveDate>())
    private val _menstruationDays = MutableStateFlow(emptyList<BraveDate>())
    private val _childBearingDays = MutableStateFlow(emptyList<BraveDate>())
    private val _ovulationDays = MutableStateFlow(emptyList<BraveDate>())
    private val _homeGraphItems = MutableStateFlow(emptyList<HomeGraphItem>())
    private val _homeMainText = MutableStateFlow("")
    private val _homeSubText = MutableStateFlow("")

    init {
        val calendar = Calendar.getInstance()
        val homeDays = mutableListOf<BraveDate>()
        repeat(7) {
            homeDays.add(
                BraveDate(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH) + 1,
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
            )
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        _homeDays.value = homeDays
        getMenstruation()
    }

    val uiState = combine(
        _homeDays, _menstruationDays, _childBearingDays, _ovulationDays, _homeGraphItems,
        _homeMainText, _homeSubText
    ) { flows ->
        @Suppress("UNCHECKED_CAST")
        HomeUiState(
            homeDays = flows[0] as List<BraveDate>,
            menstruationDays = flows[1] as List<BraveDate>,
            childBearingDays = flows[2] as List<BraveDate>,
            ovulationDays = flows[3] as List<BraveDate>,
            homeGraphItems = flows[4] as List<HomeGraphItem>,
            homeMainText = flows[5] as String,
            homeSubText = flows[6] as String
        )
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), HomeUiState()
    )

    private fun getMenstruation() = viewModelScope.launch {
        val startDay = _homeDays.value.first().format()
        val endDay = Calendar.getInstance().let {
            it.add(Calendar.DAY_OF_MONTH, 30)
            BraveDate(
                it.get(Calendar.YEAR),
                it.get(Calendar.MONTH) + 1,
                it.get(Calendar.DAY_OF_MONTH)
            ).format()
        }
        homeUseCase.getMenstruation(
            1,
            startDate = startDay,
            endDate = endDay
        ).collectLatest { apiState ->
            apiState.onSuccess { response ->
                val response = response.data ?: return@onSuccess
                response.divisionToBraveDate(dateParser)
                    .let { (menstruationDays, childBearingDays, ovulationDays) ->
                        _menstruationDays.value = menstruationDays
                        _childBearingDays.value = childBearingDays
                        _ovulationDays.value = ovulationDays
                        generateHomeGraphItems(menstruationDays, childBearingDays)
                    }
            }
        }
    }

    private fun generateHomeGraphItems(
        menstruationDays: List<BraveDate>,
        childBearingDays: List<BraveDate>,
    ) {
        val calendar = Calendar.getInstance()
        val homeGraphItems = mutableListOf<HomeGraphItem>()
        repeat(30) {
            val date = BraveDate(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            if (menstruationDays.contains(date)) {
                homeGraphItems.add(HomeGraphItem(date, DateType.MENSTRUATION))
            } else if (childBearingDays.contains(date)) {
                homeGraphItems.add(HomeGraphItem(date, DateType.CHILDBEARING))
            } else {
                homeGraphItems.add(HomeGraphItem(date, DateType.NORMAL))
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        if (homeGraphItems.first().dateType == DateType.MENSTRUATION) {
            _homeMainText.value = "생리중"
            _homeSubText.value = ""
        } else {
            _homeMainText.value = "생리까지"
            _homeSubText.value =
                "D-${homeGraphItems.indexOfFirst { it.dateType == DateType.MENSTRUATION }}"
        }
        _homeGraphItems.value = splitListByType(homeGraphItems)
    }

    private fun splitListByType(items: List<HomeGraphItem>): List<HomeGraphItem> {
        val result = mutableListOf<MutableList<HomeGraphItem>>()
        var currentList = mutableListOf<HomeGraphItem>()

        for (item in items) {
            if (currentList.isEmpty() || currentList.last().dateType != item.dateType) {
                currentList = mutableListOf()
                result.add(currentList)
            }
            currentList.add(item)
        }

        var startAngle = -90f
        return result.map { homeGraphItem ->
            val graphType = homeGraphItem.first().dateType
            val sweepAngle = homeGraphItem.size.toFloat() / result.sumOf { it.size }
                .toFloat() * 360f
            val width = when (graphType) {
                DateType.MENSTRUATION -> 30.dp
                DateType.CHILDBEARING -> 30.dp
                else -> 20.dp
            }
            val color = when (graphType) {
                DateType.MENSTRUATION ->
                    listOf(Main, Main, Color(0xFFFFC2C2))
                DateType.CHILDBEARING ->
                    listOf(Color(0xFFE742EB), Color(0xFF3D70F1))
                else -> listOf(Gray100, Gray100)
            }

            HomeGraphItem(
                homeGraphItem.first().date,
                graphType,
                color,
                width,
                startAngle,
                sweepAngle
            ).also {
                startAngle += sweepAngle
            }
        }.sortedBy { it.dateType }

    }

    fun report() = viewModelScope.launch {
        val message = withContext(Dispatchers.IO) {
            addressRepository.getToken(Constants.PREF_MESSAGE_TEXT).first()
        }
        try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(
                "01029960826",
                null,
                message,
                null,
                null
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}