package kau.brave.breakthecycle.ui

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kau.brave.breakthecycle.graph.*
import kau.brave.breakthecycle.ui.model.ApplicationState
import kau.brave.breakthecycle.ui.diary.DiaryWritePhotoScreen
import kau.brave.breakthecycle.ui.splash.SplashScreen
import kau.brave.breakthecycle.utils.Constants.DIARY_WRITE_PHOTO_ROUTE
import kau.brave.breakthecycle.utils.Constants.SPLASH_ROUTE

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RootNavhost(appState: ApplicationState) {
    Scaffold(
        scaffoldState = appState.scaffoldState,
        snackbarHost = {
            SnackbarHost(hostState = appState.scaffoldState.snackbarHostState,
                snackbar = { data ->
                    Snackbar(
                        modifier = Modifier.padding(
                            bottom = 50.dp,
                            start = 20.dp,
                            end = 20.dp
                        )
                    ) {
                        Text(text = data.message)
                    }
                }
            )
        },
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
                diaryWriteGraph(appState)
            }

            if (appState.bottomBarState.value) BottomBar(appState)
        }
    }
}


