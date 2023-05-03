package kau.brave.breakthecycle.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kau.brave.breakthecycle.ui.model.ApplicationState
import kau.brave.breakthecycle.ui.auth.signin.SignInIdPasswdScreen
import kau.brave.breakthecycle.ui.auth.signin.SignInPhoneVerify
import kau.brave.breakthecycle.utils.Constants.SIGNIN_GRAPH
import kau.brave.breakthecycle.utils.Constants.SIGNIN_ID_PASSWD_ROUTE
import kau.brave.breakthecycle.utils.Constants.SIGNIN_PHONE_VERIFY_ROUTE

fun NavGraphBuilder.signInGraph(appState: ApplicationState) {
    navigation(
        route = SIGNIN_GRAPH,
        startDestination = SIGNIN_PHONE_VERIFY_ROUTE
    ) {

        composable(SIGNIN_PHONE_VERIFY_ROUTE) {
            SignInPhoneVerify(appState)
        }
        composable(SIGNIN_ID_PASSWD_ROUTE) {
            SignInIdPasswdScreen(appState)
        }

    }
}