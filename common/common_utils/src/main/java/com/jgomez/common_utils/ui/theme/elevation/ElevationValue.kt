package com.jgomez.common_utils.ui.theme.elevation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class ElevationValue(
    val x: Dp = 0.dp, val y: Dp = 0.dp, val blur: Dp = 0.dp, val spread: Dp = 0.dp, val color: Color = Color.White,
)