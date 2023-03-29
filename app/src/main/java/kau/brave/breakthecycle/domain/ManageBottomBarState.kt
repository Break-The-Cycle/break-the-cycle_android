package kau.brave.breakthecycle.domain

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import kau.brave.breakthecycle.domain.model.ApplicationState
import kau.brave.breakthecycle.ui.model.Screen

@Composable
fun ManageBottomBarState(
    navBackStackEntry: NavBackStackEntry?,
    applicationState: ApplicationState,
) {
    when (navBackStackEntry?.destination?.route) {
        Screen.Home.route, Screen.Calendar.route, Screen.Mypage.route ->
            applicationState.bottomBarState.value = true
        else -> applicationState.bottomBarState.value = false
    }
}