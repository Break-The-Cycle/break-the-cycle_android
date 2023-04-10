package kau.brave.breakthecycle.ui.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kau.brave.breakthecycle.domain.model.ApplicationState
import kau.brave.breakthecycle.ui.auth.onboard.OnboardCycleScreen
import kau.brave.breakthecycle.ui.auth.onboard.OnboardDateWriteScreen
import kau.brave.breakthecycle.ui.auth.signin.SignInCompleteScreen
import kau.brave.breakthecycle.ui.auth.signin.SignInGenderScreen
import kau.brave.breakthecycle.ui.auth.signin.SignInSettingScreen
import kau.brave.breakthecycle.utils.Constants.ONBOARD_CYCLE_ROUTE
import kau.brave.breakthecycle.utils.Constants.ONBOARD_DATE_WRITE_ROUTE
import kau.brave.breakthecycle.utils.Constants.ONBOARD_GRAPH
import kau.brave.breakthecycle.utils.Constants.SIGNIN_COMPLETE_ROUTE
import kau.brave.breakthecycle.utils.Constants.SIGNIN_GENDER_ROUTE
import kau.brave.breakthecycle.utils.Constants.SIGNIN_SETTING_ROUTE

fun NavGraphBuilder.onboardGraph(appState: ApplicationState) {
    navigation(
        route = ONBOARD_GRAPH,
        startDestination = ONBOARD_CYCLE_ROUTE
    ) {

        /** Onboard */
        composable(ONBOARD_CYCLE_ROUTE) {
            OnboardCycleScreen(appState)
        }

        composable(SIGNIN_SETTING_ROUTE) {
            SignInSettingScreen(appState)
        }
        
        composable(ONBOARD_DATE_WRITE_ROUTE) {
            OnboardDateWriteScreen(appState)
        }
        composable(SIGNIN_GENDER_ROUTE) {
            SignInGenderScreen(appState)
        }
        composable(SIGNIN_COMPLETE_ROUTE) {
            SignInCompleteScreen(appState)
        }
    }
}