package kau.brave.breakthecycle.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import kau.brave.breakthecycle.ui.model.ApplicationState
import kau.brave.breakthecycle.ui.onboard.SecretOnboard
import kau.brave.breakthecycle.utils.Constants.SECERET_ONBOARD_ROUTE


fun NavGraphBuilder.secretOnboardGraph(appState: ApplicationState) {

    composable(
        route = "${SECERET_ONBOARD_ROUTE}/{init}",
        arguments = listOf(
            navArgument("init") {
                defaultValue = true
            }
        )
    ) { backStackEntry ->
        val init = backStackEntry.arguments?.getBoolean("init") ?: true
        SecretOnboard(
            appState = appState, init = init
        )
    }
}