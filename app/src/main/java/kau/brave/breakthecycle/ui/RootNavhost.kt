package kau.brave.breakthecycle.ui

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kau.brave.breakthecycle.domain.model.ApplicationState
import kau.brave.breakthecycle.ui.diary.DiaryWriteScreen
import kau.brave.breakthecycle.ui.graph.*
import kau.brave.breakthecycle.ui.splash.SplashScreen
import kau.brave.breakthecycle.utils.Constants.DIARY_WRITE_ROUTE
import kau.brave.breakthecycle.utils.Constants.SPLASH_ROUTE

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RootNavhost(appState: ApplicationState) {
    Scaffold(
        scaffoldState = appState.scaffoldState,
        modifier = Modifier.fillMaxSize(),
    ) { _ ->
        Column {
            NavHost(
                navController = appState.navController,
                startDestination = SPLASH_ROUTE,
                modifier = Modifier.weight(1f)
            ) {
                composable(SPLASH_ROUTE) {
                    SplashScreen(appState)
                }
                mainGraph(appState)
                authGraph(appState)
                signInGraph(appState)
                onboardGraph(appState)
                userInfoGraph(appState)
                secretOnboardGraph(appState)
                
                composable(route = DIARY_WRITE_ROUTE) {
                    val userObject =
                        appState.navController.previousBackStackEntry?.arguments?.getParcelable<Uri>(
                            "uri"
                        )
                    DiaryWriteScreen(appState, userObject)
                }
            }

            if (appState.bottomBarState.value) BottomBar(appState)
        }
    }
}


