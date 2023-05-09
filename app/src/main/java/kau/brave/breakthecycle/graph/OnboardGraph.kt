package kau.brave.breakthecycle.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import kau.brave.breakthecycle.ui.model.ApplicationState
import kau.brave.breakthecycle.ui.onboard.OnboardCycleScreen
import kau.brave.breakthecycle.utils.Constants.ONBOARD_ROUTE

fun NavGraphBuilder.onboardGraph(appState: ApplicationState) {
    composable(
        route = "$ONBOARD_ROUTE/{init}",
        arguments = listOf(
            navArgument("init") {
                defaultValue = true
            }
        )
    ) { backStackEntry ->
        val init = backStackEntry.arguments?.getBoolean("init") ?: true
        OnboardCycleScreen(appState, init)
    }
}