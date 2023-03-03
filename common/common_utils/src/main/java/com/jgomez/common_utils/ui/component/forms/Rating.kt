package com.jgomez.common_utils.ui.component.forms

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.jgomez.common_utils.R


@Composable
fun Starts(score: Double = 0.0) {
    when (score) {
        in 0.0..1.0 -> {
            Image(
                painter = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.one_star)),
                contentDescription = null
            )
        }
        in 1.0..2.0 -> {
            Image(
                painter = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.two_star)),
                contentDescription = null
            )
        }
        in 2.0..3.0 -> {
            Image(
                painter = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.three_star)),
                contentDescription = null
            )
        }
    }
}

@Composable
@Preview
fun StartPreview() {
    Starts(1.0)
}
