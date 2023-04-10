package kau.brave.breakthecycle.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kau.brave.breakthecycle.R
import kau.brave.breakthecycle.domain.model.ApplicationState
import kau.brave.breakthecycle.ui.theme.Main
import kau.brave.breakthecycle.ui.theme.White
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
        modifier = Modifier
            .fillMaxSize()
            .background(White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_logo),
            contentDescription = "IMG_LOGO",
            modifier = Modifier.size(134.dp)
        )
        Text(
            text = "ROSE DAYS",
            fontSize = 39.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black, modifier = Modifier.padding(top = 10.dp)
        )
    }
}