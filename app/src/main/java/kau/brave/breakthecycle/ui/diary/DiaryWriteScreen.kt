package kau.brave.breakthecycle.ui.diary

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import kau.brave.breakthecycle.domain.model.ApplicationState
import kau.brave.breakthecycle.ui.diary.model.DiaryWriteType

@Composable
fun DiaryWriteScreen(
    appState: ApplicationState,
    imageUri: Uri?,
) {

    var diaryWriteType by remember {
        mutableStateOf(DiaryWriteType.VIEW)
    }

    LaunchedEffect(key1 = diaryWriteType) {
        when (diaryWriteType) {
            DiaryWriteType.VIEW -> {
            }
            DiaryWriteType.WRITE -> {
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = rememberAsyncImagePainter(imageUri),
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )

        when (diaryWriteType) {
            DiaryWriteType.VIEW -> {
                Button(
                    modifier = Modifier
                        .padding(20.dp)
                        .align(Alignment.TopEnd),
                    onClick = {
                        diaryWriteType = DiaryWriteType.WRITE
                    }) {
                    Text(text = "손글씨 넣기")
                }
            }

            DiaryWriteType.WRITE -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f))
                )
            }
        }

        val drawPaths = remember {
            mutableStateListOf<List<Offset>>(emptyList())
        }

        if (DiaryWriteType.WRITE == diaryWriteType) {
            val paths = remember {
                mutableStateListOf<Offset>()
            }

            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = {
                            },
                            onDragEnd = {
                                drawPaths.add(paths)
                                paths.clear()
                                Log.i("dlgocks1", drawPaths.toString())
                            }
                        ) { change, dragAmount ->
                            paths.add(change.position)
                        }
                    }) {
                for (path in 0 until paths.size - 1) {
                    drawLine(
                        color = Color.White,
                        start = paths[path],
                        end = paths[path + 1],
                        strokeWidth = 10f
                    )
                }
                drawPaths.forEach {
                    Log.i("dlgocks1", it.toString())
                    for (path in 0 until it.size - 1) {
                        drawLine(
                            color = Color.White,
                            start = it[path],
                            end = it[path + 1],
                            strokeWidth = 10f
                        )
                    }
                }
            }

            Button(
                modifier = Modifier
                    .padding(20.dp)
                    .align(Alignment.TopEnd),
                onClick = {
                    diaryWriteType = DiaryWriteType.VIEW
                }) {
                Text(text = "완료")
            }
        }
    }
}