package kau.brave.breakthecycle

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import kau.brave.breakthecycle.domain.model.ApplicationState
import kau.brave.breakthecycle.utils.Constants

@Composable
fun BottomBar(appState: ApplicationState) {

    val bottomNavItems = Constants.BOTTOM_NAV_ITEMS

    AnimatedVisibility(
        visible = appState.bottomBarState.value,
        enter = slideInVertically { it },
        exit = slideOutVertically { it },
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Transparent)
            .navigationBarsPadding(),
    ) {
        BottomNavigation(
            backgroundColor = Color.White,
            elevation = 0.dp
        ) {
            val navBackStackEntry by appState.navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            bottomNavItems.forEachIndexed { _, screen ->
                val isSelected =
                    currentDestination?.hierarchy?.any { it.route == screen.route } == true
                BottomNavigationItem(
                    icon = {
                        Surface {
                            Icon(
                                painter = painterResource(
                                    id =
                                    (if (isSelected) screen.selecteddrawableResId else screen.drawableResId),
                                ),
                                contentDescription = null,
                            )
                        }
                    },
                    label = {
                        Text(text = screen.title)
                    },
                    selected = isSelected,
                    onClick = {
                        appState.navController.navigate(screen.route) {
                            popUpTo(Constants.MAIN_GRAPH) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    selectedContentColor = Color.Black,
                    unselectedContentColor = Color.Black,
                )
            }
        }
    }
}
