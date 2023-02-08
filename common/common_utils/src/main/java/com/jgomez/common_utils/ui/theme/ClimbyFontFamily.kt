package com.jgomez.common_utils.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.jgomez.common_utils.R

data class ClimbyFontFamily(
    val text: FontFamily =
        FontFamily(
            Font(
                resId = R.font.sfproroundedultralight,
                weight = FontWeight.Normal,
                style = FontStyle.Normal,
            ),
            Font(
                resId = R.font.sfproroundedthin,
                weight = FontWeight.Normal,
                style = FontStyle.Italic,
            ),
            Font(
                resId = R.font.sfproroundedlight,
                weight = FontWeight.Bold,
                style = FontStyle.Normal,
            ),
            Font(
                resId = R.font.sfproroundedregular,
                weight = FontWeight.Bold,
                style = FontStyle.Italic,
            ),
            Font(
                resId = R.font.sfproroundedmedium,
                weight = FontWeight.Light,
                style = FontStyle.Normal,
            ),
            Font(
                resId = R.font.sfproroundedbold,
                weight = FontWeight.Light,
                style = FontStyle.Italic,
            ),
            Font(
                resId = R.font.sfproroundedheavy,
                weight = FontWeight.Light,
                style = FontStyle.Italic,
            ),
            Font(
                resId = R.font.sfproroundedblack,
                weight = FontWeight.Light,
                style = FontStyle.Italic,
            )
        )
)

