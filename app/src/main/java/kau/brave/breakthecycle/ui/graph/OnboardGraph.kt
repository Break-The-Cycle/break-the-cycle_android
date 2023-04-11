package kau.brave.breakthecycle.ui.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kau.brave.breakthecycle.domain.model.ApplicationState
import kau.brave.breakthecycle.ui.auth.onboard.OnboardCycleScreen
import kau.brave.breakthecycle.ui.auth.onboard.OnboardDateWriteScreen
import kau.brave.breakthecycle.utils.Constants.ONBOARD_CYCLE_ROUTE
import kau.brave.breakthecycle.utils.Constants.ONBOARD_DATE_WRITE_ROUTE
import kau.brave.breakthecycle.utils.Constants.ONBOARD_GRAPH

fun NavGraphBuilder.onboardGraph(appState: ApplicationState) {
    navigation(
        route = ONBOARD_GRAPH,
        startDestination = ONBOARD_CYCLE_ROUTE
    ) {

        composable(ONBOARD_CYCLE_ROUTE) {
            OnboardCycleScreen(appState)
        }
        composable(ONBOARD_DATE_WRITE_ROUTE) {
            OnboardDateWriteScreen(appState)
        }

    }
}