package com.jgomez.common_utils.ui.component.pills

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jgomez.common_utils.R
import com.jgomez.common_utils.ui.theme.ClimbyColor

@Composable
fun Pill(painter: Painter) {
    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
            .size(32.dp)
            .border(width = 2.dp, color = ClimbyColor().n200, shape = CircleShape)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}

@Composable
@Preview
fun PillPreview() {
    Pill(painterResource(id = R.drawable.sputnik))
}