package kau.brave.breakthecycle.graph

import android.net.Uri
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kau.brave.breakthecycle.ui.diary.DiaryWritePhotoScreen
import kau.brave.breakthecycle.ui.diary.DiaryWriteScreen
import kau.brave.breakthecycle.ui.model.ApplicationState
import kau.brave.breakthecycle.ui.model.Screen
import kau.brave.breakthecycle.utils.Constants.DIARY_WRITE_GRAPH
import kau.brave.breakthecycle.utils.Constants.DIARY_WRITE_PHOTO_ROUTE
import kau.brave.breakthecycle.utils.Constants.DIARY_WRITE_ROUTE

fun NavGraphBuilder.diaryWriteGraph(appState: ApplicationState) {
    navigation(
        route = DIARY_WRITE_GRAPH,
        startDestination = DIARY_WRITE_ROUTE
    ) {

        composable(route = DIARY_WRITE_ROUTE) {
            DiaryWriteScreen(appState)
        }

        composable(route = DIARY_WRITE_PHOTO_ROUTE) {
            val userObject =
                appState.navController.previousBackStackEntry?.arguments?.getParcelable<Uri>(
                    "uri"
                )
            DiaryWritePhotoScreen(appState, userObject)
        }
    }
}