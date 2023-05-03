package kau.brave.breakthecycle.graph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kau.brave.breakthecycle.ui.auth.userinfo.*
import kau.brave.breakthecycle.ui.model.ApplicationState
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
        composable(SIGNIN_SETTING_ROUTE) { entry ->
            val parentEntry: NavBackStackEntry =
                rememberNavBackStackEntry(USERINFO_GRAPH, entry, appState)
            UserInfoSettingScreen(appState, hiltViewModel(parentEntry))
        }
        composable(USERINFO_MENSTRUATION_DURATION_ROUTE) { entry ->
            val parentEntry: NavBackStackEntry =
                rememberNavBackStackEntry(USERINFO_GRAPH, entry, appState)
            UserInfoMenstruationDuration(appState, hiltViewModel(parentEntry))
        }
        composable(USERINFO_MENSTRUATION_DATE_ROUTE) { entry ->
            val parentEntry: NavBackStackEntry =
                rememberNavBackStackEntry(USERINFO_GRAPH, entry, appState)
            UserInfoMenstruationDate(appState, hiltViewModel(parentEntry))
        }
        composable(SIGNIN_COMPLETE_ROUTE) { entry ->
            val parentEntry: NavBackStackEntry =
                rememberNavBackStackEntry(USERINFO_GRAPH, entry, appState)
            UserInfoCompleteScreen(appState, hiltViewModel(parentEntry))
        }
    }
}

@Composable
private fun rememberNavBackStackEntry(
    graph: String,
    entry: NavBackStackEntry,
    appState: ApplicationState,
) = remember(entry) {
    appState.navController.getBackStackEntry(graph)
}