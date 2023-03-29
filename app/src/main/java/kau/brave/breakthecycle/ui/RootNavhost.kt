package kau.brave.breakthecycle.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kau.brave.breakthecycle.BottomBar
import kau.brave.breakthecycle.domain.model.ApplicationState
import kau.brave.breakthecycle.ui.calendar.CalendarScreen
import kau.brave.breakthecycle.ui.home.HomeScreen
import kau.brave.breakthecycle.ui.model.Screen
import kau.brave.breakthecycle.ui.mypage.MypageScreen
import kau.brave.breakthecycle.utils.Constants
import kau.brave.breakthecycle.ui.splash.SplashScreen

@Composable
fun RootNavhost(appState: ApplicationState) {
    Scaffold(
        scaffoldState = appState.scaffoldState,
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (appState.bottomBarState.value) BottomBar(appState)
        }
    ) { innerPadding ->
        NavHost(
            navController = appState.navController,
            modifier = Modifier.padding(innerPadding),
            startDestination = Constants.SPLASH_ROUTE
        ) {
            composable(Constants.SPLASH_ROUTE) {
                SplashScreen(appState)
            }
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
    }
}