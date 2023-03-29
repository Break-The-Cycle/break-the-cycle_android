package kau.brave.breakthecycle.ui.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kau.brave.breakthecycle.domain.model.ApplicationState
import kau.brave.breakthecycle.utils.Constants.AUTH_GRAPH
import kau.brave.breakthecycle.utils.Constants.SPLASH_ROUTE
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(appState: ApplicationState) {

    LaunchedEffect(key1 = Unit) {
        delay(2000L)
        appState.navController.navigate(AUTH_GRAPH) {
            popUpTo(SPLASH_ROUTE) {
                inclusive = true
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Splash Screen")
    }
}