package kau.brave.breakthecycle.ui.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kau.brave.breakthecycle.domain.model.ApplicationState
import kau.brave.breakthecycle.ui.auth.signin.SignInCompleteScreen
import kau.brave.breakthecycle.ui.auth.signin.SignInMenstruationDate
import kau.brave.breakthecycle.ui.auth.signin.SignInMenstruationDuration
import kau.brave.breakthecycle.ui.auth.signin.SignInSettingScreen
import kau.brave.breakthecycle.utils.Constants.SIGNIN_COMPLETE_ROUTE
import kau.brave.breakthecycle.utils.Constants.SIGNIN_SETTING_ROUTE
import kau.brave.breakthecycle.utils.Constants.USERINFO_GRAPH
import kau.brave.breakthecycle.utils.Constants.USERINFO_MENSTRUATION_DATE_ROUTE
import kau.brave.breakthecycle.utils.Constants.USERINFO_MENSTRUATION_DURATION_ROUTE


fun NavGraphBuilder.userInfoGraph(appState: ApplicationState) {
    navigation(
        route = USERINFO_GRAPH,
        startDestination = SIGNIN_SETTING_ROUTE
    ) {

        composable(SIGNIN_SETTING_ROUTE) {
            SignInSettingScreen(appState)
        }
        composable(USERINFO_MENSTRUATION_DURATION_ROUTE) {
            SignInMenstruationDuration(appState)
        }
        composable(USERINFO_MENSTRUATION_DATE_ROUTE) {
            SignInMenstruationDate(appState)
        }
        composable(SIGNIN_COMPLETE_ROUTE) {
            SignInCompleteScreen(appState)
        }
    }
}