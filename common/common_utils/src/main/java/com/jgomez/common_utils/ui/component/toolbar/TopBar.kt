package com.jgomez.common_utils.ui.component.toolbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jgomez.common_utils.R
import com.jgomez.common_utils.ui.theme.ClimbyTheme
import com.jgomez.common_utils.ui.theme.text.ClimbyTextStyle
import com.jgomez.common_utils.ui.wrapper.ClimbyImage
import com.jgomez.common_utils.ui.wrapper.painter


@Composable
fun TopBar(
    icon: ClimbyImage,
    title: String,
    theme: ClimbyTheme = ClimbyTheme(),
    onClick: () -> Unit,
    fontStyle : ClimbyTextStyle = ClimbyTextStyle
) {
    TopAppBar(
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    ) {
        IconButton(onClick = { onClick() }) {
            Icon(painter = icon.painter, tint = theme.color.black, contentDescription = "Back")
        }
        Text(
            text = title,
            textAlign = TextAlign.Center,
            fontStyle =  fontStyle.H1Bold().fontStyle,
            fontWeight = fontStyle.H1Bold().fontWeight,
            fontSize = fontStyle.H1Bold().fontSize,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 48.dp)
                
        )
    }

}

@Composable
@Preview
fun TopBarPreview() {
    TopBar(ClimbyImage.Resource(id = R.drawable.arrow_back), "¿Tu información es correcta?", onClick = {})
}