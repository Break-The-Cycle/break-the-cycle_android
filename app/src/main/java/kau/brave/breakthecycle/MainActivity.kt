package kau.brave.breakthecycle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import dagger.hilt.android.AndroidEntryPoint
import kau.brave.breakthecycle.utils.ManageBottomBarState
import kau.brave.breakthecycle.utils.rememberApplicationState
import kau.brave.breakthecycle.ui.RootNavhost
import kau.brave.breakthecycle.theme.BreakTheCycleTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val appState = rememberApplicationState()
            val navBackStackEntry by appState.navController.currentBackStackEntryAsState()
            appState.uiController.setSystemBarsColor(Color.Transparent, darkIcons = true)
            appState.uiController.setNavigationBarColor(Color.Transparent)
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