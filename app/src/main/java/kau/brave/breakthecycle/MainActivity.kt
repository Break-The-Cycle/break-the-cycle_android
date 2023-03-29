package kau.brave.breakthecycle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import dagger.hilt.android.AndroidEntryPoint
import kau.brave.breakthecycle.domain.ManageBottomBarState
import kau.brave.breakthecycle.domain.rememberApplicationState
import kau.brave.breakthecycle.ui.RootNavhost
import kau.brave.breakthecycle.ui.theme.BreakTheCycleTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val appState = rememberApplicationState()
            val navBackStackEntry by appState.navController.currentBackStackEntryAsState()
            ManageBottomBarState(
                navBackStackEntry = navBackStackEntry,
                applicationState = appState
            )
            BreakTheCycleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    RootNavhost(appState)
                }
            }
        }
    }
}