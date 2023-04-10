package kau.brave.breakthecycle.ui.auth.onboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kau.brave.breakthecycle.domain.model.ApplicationState
import kau.brave.breakthecycle.utils.Constants.AUTH_GRAPH
import kau.brave.breakthecycle.utils.Constants.MAIN_GRAPH

@Composable
fun OnboardDateWriteScreen(appstate: ApplicationState) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "OnBoard Screen")
        Button(onClick = {
            appstate.navController.navigate(MAIN_GRAPH) {
                popUpTo(AUTH_GRAPH) {
                    inclusive = true
                }
            }
        }) {
            Text(text = "Navigate to Main Screen")
        }
    }
}