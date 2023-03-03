package com.jgomez.common_utils.ui.theme.text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.FontWeight
import com.jgomez.common_utils.ui.theme.ClimbyTheme
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jgomez.common_utils.ui.component.dpToSp

val LocalFontScaling = staticCompositionLocalOf { false }

object ClimbyTextStyle {

    @Composable
    @ReadOnlyComposable
    fun Heading0(fontScaling: Boolean = LocalFontScaling.current) =
        Heading0(fontScaling, FontWeight.Bold)

    @Composable
    @ReadOnlyComposable
    fun Heading1(fontScaling: Boolean = LocalFontScaling.current) =
        Heading1(fontScaling, FontWeight.Bold)

    @Composable
    @ReadOnlyComposable
    fun Heading2(fontScaling: Boolean = LocalFontScaling.current) =
        Heading2(fontScaling, FontWeight.Bold)

    @Composable
    @ReadOnlyComposable
    fun Heading3(fontScaling: Boolean = LocalFontScaling.current) =
        Heading3(fontScaling, FontWeight.Bold)

    @Composable
    @ReadOnlyComposable
    fun Heading4(fontScaling: Boolean = LocalFontScaling.current) =
        Heading4(fontScaling, FontWeight.Bold)

    @Composable
    @ReadOnlyComposable
    fun Heading4Button(fontScaling: Boolean = LocalFontScaling.current) =
        Heading4Button(fontScaling, FontWeight.Bold)

    @Composable
    @ReadOnlyComposable
    fun Heading4BodyLead(fontScaling: Boolean = LocalFontScaling.current) =
        Heading4BodyLead(fontScaling, FontWeight.Companion.Medium)


    @Composable
    @ReadOnlyComposable
    fun Heading5(fontScaling: Boolean = LocalFontScaling.current) =
        Heading5(fontScaling, FontWeight.Companion.Bold)

    @Composable
    @ReadOnlyComposable
    fun Heading5Link(fontScaling: Boolean = LocalFontScaling.current) =
        Heading5Link(fontScaling, FontWeight.Companion.Bold)

    @Composable
    @ReadOnlyComposable
    fun Heading5Value(fontScaling: Boolean = LocalFontScaling.current) =
        Heading5Value(fontScaling, FontWeight.Companion.SemiBold)

    @Composable
    @ReadOnlyComposable
    fun Heading5Label(fontScaling: Boolean = LocalFontScaling.current) =
        Heading5Label(fontScaling, FontWeight.Companion.SemiBold)

    @Composable
    @ReadOnlyComposable
    fun Heading6(fontScaling: Boolean = LocalFontScaling.current) =
        Heading6(fontScaling, FontWeight.Companion.Bold)

    @Composable
    @ReadOnlyComposable
    fun Heading6Body(fontScaling: Boolean = LocalFontScaling.current) =
        Heading6Body(fontScaling, FontWeight.Companion.Medium)

    @Composable
    @ReadOnlyComposable
    fun Caption(fontScaling: Boolean = LocalFontScaling.current) =
        Caption(fontScaling, FontWeight.Companion.Medium)


    @Composable
    @ReadOnlyComposable
    private fun Heading0(
        fontScaling: Boolean = LocalFontScaling.current,
        fontWeight: FontWeight? = null,
        theme: ClimbyTheme = ClimbyTheme()
    ) =
        TextStyle(
            color = theme.color.black,
            fontSize = if (fontScaling) 32.sp else 32.dpToSp,
            lineHeight = if (fontScaling) 40.sp else 40.dpToSp,
            fontFamily = theme.fontFamily.text,
            fontWeight = fontWeight,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        )

    @Composable
    @ReadOnlyComposable
    private fun Heading1(
        fontScaling: Boolean = LocalFontScaling.current,
        fontWeight: FontWeight? = null,
        theme: ClimbyTheme = ClimbyTheme()
    ) =
        TextStyle(
            color = theme.color.black,
            fontSize = if (fontScaling) 28.sp else 28.dpToSp,
            lineHeight = if (fontScaling) 32.sp else 32.dpToSp,
            fontFamily = theme.fontFamily.text,
            fontWeight = fontWeight,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        )

    @Composable
    @ReadOnlyComposable
    private fun Heading2(
        fontScaling: Boolean = LocalFontScaling.current,
        fontWeight: FontWeight? = null,
        theme: ClimbyTheme = ClimbyTheme()
    ) =
        TextStyle(
            color = theme.color.black,
            fontSize = if (fontScaling) 24.sp else 24.dpToSp,
            lineHeight = if (fontScaling) 32.sp else 32.dpToSp,
            fontFamily = theme.fontFamily.text,
            fontWeight = fontWeight,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        )

    @Composable
    @ReadOnlyComposable
    private fun Heading3(
        fontScaling: Boolean = LocalFontScaling.current,
        fontWeight: FontWeight? = null,
        theme: ClimbyTheme = ClimbyTheme()
    ) =
        TextStyle(
            color = theme.color.black,
            fontSize = if (fontScaling) 20.sp else 20.dpToSp,
            lineHeight = if (fontScaling) 24.sp else 24.dpToSp,
            fontFamily = theme.fontFamily.text,
            fontWeight = fontWeight,
            platformStyle = PlatformTextStyle(includeFontPadding = false)

        )

    @Composable
    @ReadOnlyComposable
    private fun Heading4(
        fontScaling: Boolean = LocalFontScaling.current,
        fontWeight: FontWeight? = null,
        theme: ClimbyTheme = ClimbyTheme()
    ) =
        TextStyle(
            color = theme.color.black,
            fontSize = if (fontScaling) 18.sp else 18.dpToSp,
            lineHeight = if (fontScaling) 24.sp else 24.dpToSp,
            fontFamily = theme.fontFamily.text,
            fontWeight = fontWeight,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        )

    @Composable
    @ReadOnlyComposable
    private fun Heading4Button(
        fontScaling: Boolean = LocalFontScaling.current,
        fontWeight: FontWeight? = null,
        theme: ClimbyTheme = ClimbyTheme()
    ) =
        TextStyle(
            color = theme.color.black,
            fontSize = if (fontScaling) 18.sp else 18.dpToSp,
            lineHeight = if (fontScaling) 24.sp else 24.dpToSp,
            fontFamily = theme.fontFamily.text,
            fontWeight = fontWeight,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        )

    @Composable
    @ReadOnlyComposable
    private fun Heading4BodyLead(
        fontScaling: Boolean = LocalFontScaling.current,
        fontWeight: FontWeight? = null,
        theme: ClimbyTheme = ClimbyTheme()
    ) =
        TextStyle(
            color = theme.color.black,
            fontSize = if (fontScaling) 18.sp else 18.dpToSp,
            lineHeight = if (fontScaling) 24.sp else 24.dpToSp,
            fontFamily = theme.fontFamily.text,
            fontWeight = fontWeight,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        )

    @Composable
    @ReadOnlyComposable
    private fun Heading5(
        fontScaling: Boolean = LocalFontScaling.current,
        fontWeight: FontWeight? = null,
        theme: ClimbyTheme = ClimbyTheme()
    ) =
        TextStyle(
            color = theme.color.black,
            letterSpacing = (-0.18).sp,
            fontSize = if (fontScaling) 16.sp else 16.dpToSp,
            lineHeight = if (fontScaling) 24.sp else 24.dpToSp,
            fontFamily = theme.fontFamily.text,
            fontWeight = fontWeight,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        )

    @Composable
    @ReadOnlyComposable
    private fun Heading5Link(
        fontScaling: Boolean = LocalFontScaling.current,
        fontWeight: FontWeight? = null,
        theme: ClimbyTheme = ClimbyTheme()
    ) =
        TextStyle(
            color = theme.color.black,
            fontSize = if (fontScaling) 16.sp else 16.dpToSp,
            lineHeight = if (fontScaling) 24.sp else 24.dpToSp,
            fontFamily = theme.fontFamily.text,
            fontWeight = fontWeight,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        )

    @Composable
    @ReadOnlyComposable
    private fun Heading5Value(
        fontScaling: Boolean = LocalFontScaling.current,
        fontWeight: FontWeight? = null,
        theme: ClimbyTheme = ClimbyTheme()
    ) =
        TextStyle(
            color = theme.color.black,
            fontSize = if (fontScaling) 16.sp else 16.dpToSp,
            lineHeight = if (fontScaling) 24.sp else 24.dpToSp,
            fontFamily = theme.fontFamily.text,
            fontWeight = fontWeight,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        )

    @Composable
    @ReadOnlyComposable
    private fun Heading5Label(
        fontScaling: Boolean = LocalFontScaling.current,
        fontWeight: FontWeight? = null,
        theme: ClimbyTheme = ClimbyTheme()
    ) =
        TextStyle(
            color = theme.color.black,
            letterSpacing = (-0.18).sp,
            fontSize = if (fontScaling) 16.sp else 16.dpToSp,
            lineHeight = if (fontScaling) 24.sp else 24.dpToSp,
            fontFamily = theme.fontFamily.text,
            fontWeight = fontWeight,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        )

    @Composable
    @ReadOnlyComposable
    private fun Heading6(
        fontScaling: Boolean = LocalFontScaling.current,
        fontWeight: FontWeight? = null,
        theme: ClimbyTheme = ClimbyTheme()
    ) =
        TextStyle(
            color = theme.color.black,
            fontSize = if (fontScaling) 14.sp else 14.dpToSp,
            lineHeight = if (fontScaling) 20.sp else 20.dpToSp,
            fontFamily = theme.fontFamily.text,
            fontWeight = fontWeight,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        )

    @Composable
    @ReadOnlyComposable
    private fun Heading6Body(
        fontScaling: Boolean = LocalFontScaling.current,
        fontWeight: FontWeight? = null,
        theme: ClimbyTheme = ClimbyTheme()
    ) =
        TextStyle(
            color = theme.color.black,
            fontSize = if (fontScaling) 14.sp else 14.dpToSp,
            lineHeight = if (fontScaling) 20.sp else 20.dpToSp,
            fontFamily = theme.fontFamily.text,
            fontWeight = fontWeight,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        )

    @Composable
    @ReadOnlyComposable
    private fun Caption(
        fontScaling: Boolean = LocalFontScaling.current,
        fontWeight: FontWeight? = null,
        theme: ClimbyTheme = ClimbyTheme()
    ) =
        TextStyle(
            fontSize = if (fontScaling) 13.sp else 13.dpToSp,
            lineHeight = if (fontScaling) 16.sp else 16.dpToSp,
            fontFamily = theme.fontFamily.text,
            fontWeight = fontWeight,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        )

}