@file:OptIn(ExperimentalMaterialApi::class)

package kau.brave.breakthecycle.ui.calendar

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kau.brave.breakthecycle.domain.model.BraveDate
import kau.brave.breakthecycle.ui.calendar.components.CalendarBottomSheetContents
import kau.brave.breakthecycle.ui.calendar.components.CalendarView
import kau.brave.breakthecycle.ui.calendar.viewmodel.CalendarViewModel
import kau.brave.breakthecycle.ui.component.BraveLogoIcon
import kau.brave.breakthecycle.ui.model.ApplicationState
import kau.brave.breakthecycle.utils.Constants
import kau.brave.breakthecycle.utils.Constants.DIARY_WRITE_ROUTE
import kau.brave.breakthecycle.utils.rememberApplicationState
import kotlinx.coroutines.launch
import okhttp3.internal.format
import java.util.*

@Preview
@Composable
fun CalendarScreen(appState: ApplicationState = rememberApplicationState()) {

    val viewModel: CalendarViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val secretUiState by viewModel.secretUiState.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val localDensity = LocalDensity.current
    var contentsHeight by remember {
        mutableStateOf(0.dp)
    }
    var pickHeight by remember {
        mutableStateOf(0.dp)
    }
    var dialogVisiblity by remember {
        mutableStateOf(false)
    }
    val navigateToDiaryWrite = {
        uiState.selectedDay.format()
        appState.navigate("$DIARY_WRITE_ROUTE/${uiState.selectedDay.format()}")
    }
    val navigateToDetail: (BraveDate) -> Unit = { targetDate ->
        appState.navigate("${Constants.DIARY_DETAIL_ROUTE}/${targetDate.format()}")
    }
    LaunchedEffect(key1 = contentsHeight) {
        pickHeight = screenHeight - contentsHeight + 106.dp
    }

    BottomSheetScaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        sheetPeekHeight = pickHeight, // 바텀 네비게이션바
        sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        scaffoldState = bottomSheetScaffoldState,
        sheetElevation = 0.dp,
        sheetContent = {
            CalendarBottomSheetContents(
                screenHeight = screenHeight,
                pickHeight = pickHeight,
                selectedDateType = uiState.selectedDateType,
                selectedDay = uiState.selectedDay,
                navigateToDiaryDetail = navigateToDetail,
                navigateToDiaryWrite = navigateToDiaryWrite,
                updateDialogVisibiliy = { dialogVisiblity = it },
                violentDiaries = secretUiState.violentDiary,
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .onGloballyPositioned {
                    contentsHeight = with(localDensity) { it.size.height.toDp() }
                },
        ) {
            BraveLogoIcon {
                scope.launch {
                    appState.showSnackbar("시크릿 모드에 진입했습니다.")
                    bottomSheetScaffoldState.bottomSheetState.collapse()
                }
            }
            CalendarView(
                setSelectedDay = viewModel::setSelectedDay,
                selectedDay = uiState.selectedDay,
                violentDays = secretUiState.violentDays,
                menstruationDays = uiState.menstruationDays,
                childBearingDays = uiState.childBearingDays,
                ovulationDays = uiState.ovulationDays,
                updateRange = viewModel::updateRange,
            )
        }
    }

    EmotionDilaog(
        dialogVisiblity = dialogVisiblity,
        updateDialogVisibility = { dialogVisiblity = it },
    )

}

