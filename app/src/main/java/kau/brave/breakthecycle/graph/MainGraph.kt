package kau.brave.breakthecycle.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kau.brave.breakthecycle.ui.model.ApplicationState
import kau.brave.breakthecycle.ui.calendar.CalendarScreen
import kau.brave.breakthecycle.ui.home.view.HomeScreen
import kau.brave.breakthecycle.ui.model.Screen
import kau.brave.breakthecycle.ui.mypage.MypageScreen
import kau.brave.breakthecycle.utils.Constants

fun NavGraphBuilder.mainGraph(appState: ApplicationState) {
    navigation(
        route = Constants.MAIN_GRAPH,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(appState)
        }
        composable(Screen.Calendar.route) {
            CalendarScreen(appState)
        }
        composable(Screen.Mypage.route) {
            MypageScreen(appState)
        }
    }
}