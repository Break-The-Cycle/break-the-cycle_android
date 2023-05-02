package kau.brave.breakthecycle.ui.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kau.brave.breakthecycle.domain.model.ApplicationState
import kau.brave.breakthecycle.ui.auth.secretonboard.SecretOnboard
import kau.brave.breakthecycle.utils.Constants.SECERET_ONBOARD_ROUTE
import kau.brave.breakthecycle.utils.Constants.SECERT_ONBOARD_GRAPH


fun NavGraphBuilder.secretOnboardGraph(appState: ApplicationState) {

    navigation(
        route = SECERT_ONBOARD_GRAPH,
        startDestination = SECERET_ONBOARD_ROUTE
    ) {
        composable(SECERET_ONBOARD_ROUTE) {
            SecretOnboard(appState)
        }
    }

}