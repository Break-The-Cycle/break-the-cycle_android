package kau.brave.breakthecycle.ui.diary

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import kau.brave.breakthecycle.domain.model.ApplicationState

@Composable
fun DiaryWriteScreen(
    appState: ApplicationState,
    imageUri: Uri?,
) {

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = rememberAsyncImagePainter(imageUri),
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
    }

}