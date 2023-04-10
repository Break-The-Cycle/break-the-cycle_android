@file:OptIn(ExperimentalAnimationApi::class)

package kau.brave.breakthecycle.ui

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import kau.brave.breakthecycle.domain.model.ApplicationState
import kau.brave.breakthecycle.ui.theme.*
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
            .padding(start = 32.dp, end = 32.dp, bottom = 10.dp)
            .background(color = Color.Transparent)
            .navigationBarsPadding(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(76.dp)
                .clip(RoundedCornerShape(50))
                .border(BorderStroke(1.dp, Gray100), RoundedCornerShape(50))
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val navBackStackEntry by appState.navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            bottomNavItems.forEachIndexed { _, screen ->
                val isSelected =
                    currentDestination?.hierarchy?.any { it.route == screen.route } == true
                Box(modifier = Modifier.fillMaxHeight()) {
                    IconButton(
                        modifier = Modifier
                            .size(54.dp)
                            .align(Alignment.Center),
                        onClick = {
                            if (!isSelected) {
                                appState.navController.navigate(screen.route) {
                                    popUpTo(Constants.MAIN_GRAPH) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                    ) {
                        Icon(
                            painter = painterResource(
                                id = (if (isSelected) screen.selecteddrawableResId else screen.drawableResId)
                            ),
                            contentDescription = null,
                            tint = if (isSelected) Main else Color(0xFF7C7C7C),
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    Canvas(
                        modifier = Modifier
                            .width(35.dp)
                            .align(Alignment.TopCenter)
                    ) {
                        drawLine(
                            color = if (isSelected) Main else Color.Transparent,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = 10.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }

                }
            }
        }
    }
}
