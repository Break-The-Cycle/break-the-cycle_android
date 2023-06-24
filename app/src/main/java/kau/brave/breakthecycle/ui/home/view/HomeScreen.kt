package kau.brave.breakthecycle.ui.home.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kau.brave.breakthecycle.ui.component.BraveLogoIcon
import kau.brave.breakthecycle.ui.home.HomeViewModel
import kau.brave.breakthecycle.ui.home.components.HomeCircleGraph
import kau.brave.breakthecycle.ui.home.components.HomeLegend
import kau.brave.breakthecycle.ui.home.components.WeekCalendar
import kau.brave.breakthecycle.ui.model.ApplicationState

@Composable
fun HomeScreen(appState: ApplicationState) {

    val viewModel: HomeViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
    ) {
        BraveLogoIcon {
            appState.showSnackbar("시크릿 모드에 진입했습니다.")
        }
        WeekCalendar(
            homeDays = uiState.homeDays,
            menstruationDays = uiState.menstruationDays,
            childBearingDays = uiState.childBearingDays,
            ovulationDays = uiState.ovulationDays
        )
        HomeCircleGraph(
            homeGraphItems = uiState.homeGraphItems,
            homeMainText = uiState.homeMainText,
            homeSubText = uiState.homeSubText,
            report = {
                viewModel.report()
            }
        )
        HomeLegend()
    }
}
