package kau.brave.breakthecycle.ui

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kau.brave.breakthecycle.BottomBar
import kau.brave.breakthecycle.domain.model.ApplicationState
import kau.brave.breakthecycle.ui.diary.DiaryWriteScreen
import kau.brave.breakthecycle.ui.graph.authGraph
import kau.brave.breakthecycle.ui.graph.mainGraph
import kau.brave.breakthecycle.ui.splash.SplashScreen
import kau.brave.breakthecycle.utils.Constants
import kau.brave.breakthecycle.utils.Constants.DIARY_WRITE_ROUTE
import kau.brave.breakthecycle.utils.Constants.SPLASH_ROUTE

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
            composable(SPLASH_ROUTE) {
                SplashScreen(appState)
            }
            mainGraph(appState)
            authGraph(appState)
            composable(route = DIARY_WRITE_ROUTE) {
                val userObject =
                    appState.navController.previousBackStackEntry?.arguments?.getParcelable<Uri>(
                        "uri"
                    )
                DiaryWriteScreen(appState, userObject)
            }
        }

    }
}
