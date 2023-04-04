package kau.brave.breakthecycle.ui.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kau.brave.breakthecycle.domain.model.ApplicationState
import kau.brave.breakthecycle.ui.auth.OnboardScreen
import kau.brave.breakthecycle.ui.auth.SignInGenderScreen
import kau.brave.breakthecycle.ui.auth.login.LoginIdPasswdScreen
import kau.brave.breakthecycle.utils.Constants.AUTH_GRAPH
import kau.brave.breakthecycle.utils.Constants.LOGIN_ROUTE
import kau.brave.breakthecycle.utils.Constants.ONBOARD_ROUTE
import kau.brave.breakthecycle.utils.Constants.SIGNIN_GENDER_ROUTE

fun NavGraphBuilder.authGraph(appState: ApplicationState) {
    navigation(
        route = AUTH_GRAPH,
        startDestination = LOGIN_ROUTE
    ) {
        composable(LOGIN_ROUTE) {
            LoginIdPasswdScreen(appState)
        }
        composable(SIGNIN_GENDER_ROUTE) {
            SignInGenderScreen(appState)
        }
        composable(ONBOARD_ROUTE) {
            OnboardScreen(appState)
        }
    }
}