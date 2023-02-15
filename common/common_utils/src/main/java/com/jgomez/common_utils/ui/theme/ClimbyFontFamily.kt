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
                resId = R.font.sfproroundedlight,
                weight = FontWeight.Light,
                style = FontStyle.Normal,
            ),
            Font(
                resId = R.font.sfproroundedregular,
                weight = FontWeight.Normal,
                style = FontStyle.Normal,
            ),
            Font(
                resId = R.font.sfproroundedbold,
                weight = FontWeight.Bold,
                style = FontStyle.Normal,
            )
        )
)

