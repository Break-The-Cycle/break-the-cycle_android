package kau.brave.breakthecycle.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kau.brave.breakthecycle.BottomBar
import kau.brave.breakthecycle.domain.model.ApplicationState
import kau.brave.breakthecycle.ui.graph.authGraph
import kau.brave.breakthecycle.ui.graph.mainGraph
import kau.brave.breakthecycle.ui.splash.SplashScreen
import kau.brave.breakthecycle.utils.Constants

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
            mainGraph(appState)
            authGraph(appState)
        }
    }
}