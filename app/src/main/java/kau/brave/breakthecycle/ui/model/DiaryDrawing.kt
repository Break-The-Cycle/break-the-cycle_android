package kau.brave.breakthecycle.ui.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

data class DiaryDrawing(
    val paths: List<Offset>,
    val color: Color,
    val strokeWidth: Float,
)