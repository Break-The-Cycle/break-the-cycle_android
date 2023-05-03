package kau.brave.breakthecycle.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kau.brave.breakthecycle.ui.model.ApplicationState
import kau.brave.breakthecycle.ui.auth.onboard.OnboardCycleScreen
import kau.brave.breakthecycle.utils.Constants.ONBOARD_ROUTE
import kau.brave.breakthecycle.utils.Constants.ONBOARD_GRAPH

fun NavGraphBuilder.onboardGraph(appState: ApplicationState) {
    navigation(
        route = ONBOARD_GRAPH,
        startDestination = ONBOARD_ROUTE
    ) {

        composable(ONBOARD_ROUTE) {
            OnboardCycleScreen(appState)
        }
    }
}