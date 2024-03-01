package com.example.aisample.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class DrawScreenModel(
    val linesList: List<Line> = mutableListOf(),
    val predictionInfo: String = ""
)

data class Line(
    val start: Offset,
    val end: Offset,
    val strokeWidth: Dp = 70.dp,
    val color: Color = Color.White
)