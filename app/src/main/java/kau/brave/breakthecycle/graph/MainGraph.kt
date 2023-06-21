package kau.brave.breakthecycle.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import kau.brave.breakthecycle.ui.model.ApplicationState
import kau.brave.breakthecycle.ui.calendar.CalendarScreen
import kau.brave.breakthecycle.ui.diary.DiaryDetailScreen
import kau.brave.breakthecycle.ui.diary.DiaryWriteScreen
import kau.brave.breakthecycle.ui.home.view.HomeScreen
import kau.brave.breakthecycle.ui.model.Screen
import kau.brave.breakthecycle.ui.mypage.DataExportScreen
import kau.brave.breakthecycle.ui.mypage.MypageScreen
import kau.brave.breakthecycle.ui.mypage.ReportAddressScreen
import kau.brave.breakthecycle.utils.Constants
import kau.brave.breakthecycle.utils.Constants.DATA_EXPORT_ROUTE
import kau.brave.breakthecycle.utils.Constants.DIARY_DETAIL_ROUTE
import kau.brave.breakthecycle.utils.Constants.REPORT_ADDRESS_ROUTE

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

        composable(
            route = "${DIARY_DETAIL_ROUTE}/{targetDate}",
            arguments = listOf(
                navArgument("targetDate") {
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            val targetDate = backStackEntry.arguments?.getString("targetDate") ?: ""
            DiaryDetailScreen(
                appState = appState,
                targetDate = targetDate
            )
        }

        composable(DATA_EXPORT_ROUTE) {
            DataExportScreen(appState)
        }

        composable(REPORT_ADDRESS_ROUTE) {
            ReportAddressScreen(appState)
        }

    }
}