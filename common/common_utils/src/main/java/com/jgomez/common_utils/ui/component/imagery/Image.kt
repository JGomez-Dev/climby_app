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
import kotlin.random.Random

@Composable
fun Image(imageType: Painter) {
    Image(
        painter = imageType,
        contentDescription = null,
        modifier = Modifier
            .size(104.dp)
            .clip(RoundedCornerShape(topEnd = 8.dp)),
        contentScale = ContentScale.Crop
    )
    Image(
        painter = calculateRandomMark(),
        contentDescription = null,
        modifier = Modifier
            .size(104.dp)
            .clip(RoundedCornerShape(topEnd = 8.dp)),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun calculateRandomMark(): Painter {
    val random = Random.nextInt(0,3)
    var mark: Painter = painterResource(id = R.drawable.mark_01)
    if (random == 0)
        mark = painterResource(id = R.drawable.mark_01)
    if (random == 1)
        mark = painterResource(id = R.drawable.mark_02)
    if (random == 2)
        mark = painterResource(id = R.drawable.mark_03)
    return mark
}

@Composable
@Preview
fun PillPreview() {
    Image(painterResource(id = R.drawable.sputnik))
}