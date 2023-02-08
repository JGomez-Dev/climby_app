package com.jgomez.common_utils.ui.theme.elevation

import androidx.compose.ui.unit.dp
import com.jgomez.common_utils.ui.theme.ClimbyColor

data class Elevation(
    private val color: ClimbyColor,
    val elevation00: ElevationValue = ElevationValue(
        x = 0.dp, y = 0.dp, blur = 0.dp, spread = 0.dp, color = color.colorElevation06
    ),
    val elevation01: ElevationValue = ElevationValue(
        x = 0.dp, y = 0.dp, blur = 4.dp, spread = 0.dp, color = color.colorElevation06
    ),
    val elevation02: ElevationValue = ElevationValue(
        x = 0.dp, y = 1.dp, blur = 6.dp, spread = 0.dp, color = color.colorElevation06
    ),
    val elevation03: ElevationValue = ElevationValue(
        x = 0.dp, y = 1.dp, blur = 10.dp, spread = 0.dp, color = color.colorElevation06
    ),
    val elevation20: ElevationValue = ElevationValue(
        x = 0.dp, y = (-1.00).dp, blur = 10.dp, spread = 0.dp, color = color.colorElevation06
    )
)

