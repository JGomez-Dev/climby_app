package com.jgomez.common_utils.ui.component.toolbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
        IconButton(onClick = onClick) {
            Icon(painter = icon.painter, tint = theme.color.black, contentDescription = "Back")
        }
        Text(
            text = title,
            textAlign = TextAlign.Center,
            style = fontStyle.Heading5(),
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