package com.jgomez.common_utils.ui.theme.text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.font.FontWeight
import com.jgomez.common_utils.ui.theme.ClimbyTheme
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.jgomez.common_utils.ui.component.dpToSp

val LocalFontScaling = staticCompositionLocalOf { false }

object ClimbyTextStyle {

    @Composable
    @ReadOnlyComposable
    fun H1Bold(fontScaling: Boolean = LocalFontScaling.current) = H1(fontScaling, FontWeight.Bold)

    @Composable
    @ReadOnlyComposable
    private fun H1(fontScaling: Boolean = LocalFontScaling.current, fontWeight: FontWeight? = null, theme : ClimbyTheme = ClimbyTheme()) =
        TextStyle(
            color = theme.color.black,
            fontSize = if(fontScaling) 18.sp else 18.dpToSp,
            lineHeight = if(fontScaling) 24.sp else 24.dpToSp,
            fontFamily = theme.fontFamily.text,
            fontWeight = fontWeight
        )
}