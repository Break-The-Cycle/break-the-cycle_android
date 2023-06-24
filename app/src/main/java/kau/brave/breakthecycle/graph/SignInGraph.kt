package kau.brave.breakthecycle.graph

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kau.brave.breakthecycle.ui.model.ApplicationState
import kau.brave.breakthecycle.ui.auth.signin.SignInIdPasswdScreen
import kau.brave.breakthecycle.ui.auth.signin.SignInPhoneVerify
import kau.brave.breakthecycle.utils.Constants
import kau.brave.breakthecycle.utils.Constants.SIGNIN_GRAPH
import kau.brave.breakthecycle.utils.Constants.SIGNIN_ID_PASSWD_ROUTE
import kau.brave.breakthecycle.utils.Constants.SIGNIN_PHONE_VERIFY_ROUTE

fun NavGraphBuilder.signInGraph(appState: ApplicationState) {
    navigation(
        route = SIGNIN_GRAPH,
        startDestination = SIGNIN_PHONE_VERIFY_ROUTE
    ) {

        composable(SIGNIN_PHONE_VERIFY_ROUTE) { entry ->
            val parentEntry: NavBackStackEntry =
                rememberNavBackStackEntry(SIGNIN_GRAPH, entry, appState)
            SignInPhoneVerify(appState, hiltViewModel(parentEntry))
        }
        composable(SIGNIN_ID_PASSWD_ROUTE) { entry ->
            val parentEntry: NavBackStackEntry =
                rememberNavBackStackEntry(SIGNIN_GRAPH, entry, appState)
            SignInIdPasswdScreen(appState, hiltViewModel(parentEntry))
        }

    }
}