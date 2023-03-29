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
import kau.brave.breakthecycle.utils.Constants.MAIN_GRAPH
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(appState: ApplicationState) {

    LaunchedEffect(key1 = Unit) {
        delay(2000L)
        appState.navigate(MAIN_GRAPH)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Splash Screen")
    }
}