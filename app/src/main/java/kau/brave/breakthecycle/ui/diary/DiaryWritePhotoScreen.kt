package kau.brave.breakthecycle.ui.diary

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import kau.brave.breakthecycle.ui.model.ApplicationState
import kau.brave.breakthecycle.ui.model.DiaryDrawing
import kau.brave.breakthecycle.ui.model.DiaryWriteType

@Composable
fun DiaryWritePhotoScreen(
    appState: ApplicationState,
    imageUri: Uri?,
) {

    var diaryWriteType by remember {
        mutableStateOf(DiaryWriteType.VIEW)
    }

    // 다 그려진 패스
    val drawPaths = remember {
        mutableStateListOf<DiaryDrawing>()
    }

    var strokeColor by remember {
        mutableStateOf(Color.White)
    }

    var strokeWidth by remember {
        mutableStateOf(10f)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = rememberAsyncImagePainter(imageUri),
            contentScale = ContentScale.Crop,
            contentDescription = "SELECETD_IMG",
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

        if (DiaryWriteType.WRITE == diaryWriteType) {
            OnDrawings(
                addDrawing = { drawPaths.add(it) },
                onConfirm = {
                    diaryWriteType = DiaryWriteType.VIEW
                },
                strokeColor = strokeColor,
                strokeWidth = strokeWidth
            )

            Row(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    onClick = {
                        strokeColor = Color.White
                    }) {
                    Text(text = "흰색", color = Color.Black)
                }

                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
                    onClick = {
                        strokeColor = Color.Black
                    }) {
                    Text(text = "검정", color = Color.White)
                }

                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
                    onClick = {
                        strokeWidth += 5f
                    }) {
                    Text(text = "+", color = Color.White)
                }

                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
                    onClick = {
                        strokeWidth -= 5f
                    }) {
                    Text(text = "-", color = Color.White)
                }
            }
        }
        TotalDrawings(drawPaths)
    }
}

@Composable
private fun BoxScope.OnDrawings(
    addDrawing: (DiaryDrawing) -> Unit,
    onConfirm: () -> Unit,
    strokeColor: Color,
    strokeWidth: Float,
) {
    val paths = remember {
        mutableStateListOf<Offset>()
    }
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(strokeColor, strokeWidth) {
                detectDragGestures(
                    onDragStart = {
                    },
                    onDragEnd = {
                        Log.i("dlgocks1 - added strokwidth", strokeWidth.toString())
                        addDrawing(
                            DiaryDrawing(
                                paths = paths.toList(),
                                color = strokeColor,
                                strokeWidth = strokeWidth
                            )
                        )
                        paths.clear()
                    }
                ) { change, _ ->
                    paths.add(change.position)
                }
            }) {
        for (path in 0 until paths.size - 1) {
            drawLine(
                color = strokeColor,
                start = paths[path],
                end = paths[path + 1],
                strokeWidth = strokeWidth
            )
        }
    }

    Button(
        modifier = Modifier
            .padding(20.dp)
            .align(Alignment.TopEnd),
        onClick = onConfirm
    ) {
        Text(text = "완료")
    }
}

@Composable
private fun TotalDrawings(drawPaths: List<DiaryDrawing>) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawPaths.forEach {
            Log.i("dlgocks1", it.strokeWidth.toString())
            for (path in 0 until it.paths.size - 1) {
                drawLine(
                    color = it.color,
                    start = it.paths[path],
                    end = it.paths[path + 1],
                    strokeWidth = it.strokeWidth
                )
            }
        }
    }
}