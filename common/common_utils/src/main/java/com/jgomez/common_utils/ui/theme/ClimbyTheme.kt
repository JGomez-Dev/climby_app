package com.jgomez.common_utils.ui.theme

import androidx.compose.runtime.Immutable
import com.jgomez.common_utils.ui.theme.elevation.Elevation
import com.jgomez.common_utils.ui.theme.text.ClimbyTextStyle

@Immutable
data class ClimbyTheme(
    val color: ClimbyColor = ClimbyColor(),
    val borderRadius: BorderRadius = BorderRadius(),
    val elevation: Elevation =  Elevation(color),
    val fontFamily: ClimbyFontFamily = ClimbyFontFamily(),
    val padding: Padding = Padding(),
    val textStyle : ClimbyTextStyle = ClimbyTextStyle
)
