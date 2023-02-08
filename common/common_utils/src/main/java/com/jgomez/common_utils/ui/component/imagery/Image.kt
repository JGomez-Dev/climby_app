package com.jgomez.common_utils.ui.component.imagery

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jgomez.common_utils.R


@Composable
fun Image(imageType: Painter, imageMark : Painter) {
    Image(
        painter = imageType,
        contentDescription = null,
        modifier = Modifier
            .size(104.dp)
            .clip(RoundedCornerShape(topEnd = 8.dp)),
        contentScale = ContentScale.Crop
    )
    Image(
        painter = imageMark,
        contentDescription = null,
        modifier = Modifier
            .size(104.dp)
            .clip(RoundedCornerShape(topEnd = 8.dp)),
        contentScale = ContentScale.Crop
    )
   /* Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
            .size(104.dp)
            .clip(RoundedCornerShape(topEnd = 8.dp)),
        contentScale = ContentScale.Crop
    )*/
}

@Composable
@Preview
fun PillPreview() {
    Image(painterResource(id = R.drawable.sputnik), painterResource(id = R.drawable.mark_01))
}