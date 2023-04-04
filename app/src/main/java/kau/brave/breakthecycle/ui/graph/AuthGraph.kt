package kau.brave.breakthecycle.ui.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kau.brave.breakthecycle.domain.model.ApplicationState
import kau.brave.breakthecycle.ui.auth.OnboardScreen
import kau.brave.breakthecycle.ui.auth.SignInCompleteScreen
import kau.brave.breakthecycle.ui.auth.SignInGenderScreen
import kau.brave.breakthecycle.ui.auth.SignInIdPasswd
import kau.brave.breakthecycle.ui.auth.login.LoginIdPasswdScreen
import kau.brave.breakthecycle.ui.auth.signin.SignInPhoneVerify
import kau.brave.breakthecycle.utils.Constants.AUTH_GRAPH
import kau.brave.breakthecycle.utils.Constants.LOGIN_ROUTE
import kau.brave.breakthecycle.utils.Constants.ONBOARD_ROUTE
import kau.brave.breakthecycle.utils.Constants.SIGNIN_COMPLETE_ROUTE
import kau.brave.breakthecycle.utils.Constants.SIGNIN_GENDER_ROUTE
import kau.brave.breakthecycle.utils.Constants.SIGNIN_ID_PASSWD_ROUTE
import kau.brave.breakthecycle.utils.Constants.SIGNIN_PHONE_VERIFY_ROUTE

fun NavGraphBuilder.authGraph(appState: ApplicationState) {
    navigation(
        route = AUTH_GRAPH,
        startDestination = LOGIN_ROUTE
    ) {
        composable(LOGIN_ROUTE) {
            LoginIdPasswdScreen(appState)
        }
        composable(ONBOARD_ROUTE) {
            OnboardScreen(appState)
        }

        composable(SIGNIN_GENDER_ROUTE) {
            SignInGenderScreen(appState)
        }
        composable(SIGNIN_PHONE_VERIFY_ROUTE) {
            SignInPhoneVerify(appState)
        }
        composable(SIGNIN_COMPLETE_ROUTE) {
            SignInCompleteScreen(appState)
        }
        composable(SIGNIN_ID_PASSWD_ROUTE) {
            SignInIdPasswd(appState)
        }
    }
}