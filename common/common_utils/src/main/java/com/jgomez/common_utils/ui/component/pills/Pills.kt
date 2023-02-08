package com.jgomez.common_utils.ui.component.pills

import androidx.annotation.FloatRange
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jgomez.common_utils.R
import com.jgomez.common_utils.ui.theme.ClimbyColor

const val NUMBER_BUBBLES = 4

@Composable
fun Pills(painters: List<Painter>) {
    Row(
        modifier = Modifier
            .background(color = ClimbyColor().n200, shape = CircleShape)
            .padding(all = 8.dp)
    ) {
        OverlappingRow(
            overlapFactor = 0.7f,
        ) {
            painters.take(NUMBER_BUBBLES).forEach {
                Pill(it)
            }
        }
        if (painters.size > NUMBER_BUBBLES) {
            Box(
                modifier = Modifier.align(CenterVertically),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = "+" + (painters.size - NUMBER_BUBBLES).toString(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
@Preview
fun PillsPreview() {
    val painterOneUser = listOf(
        painterResource(id = R.drawable.sputnik),
    )
    val painterTwoUsers = listOf(
        painterResource(id = R.drawable.sputnik),
        painterResource(id = R.drawable.sputnik)
    )
    val painterThreeUsers = listOf(
        painterResource(id = R.drawable.sputnik),
        painterResource(id = R.drawable.sputnik),
        painterResource(id = R.drawable.sputnik)
    )
    val painterFourUsers = listOf(
        painterResource(id = R.drawable.sputnik),
        painterResource(id = R.drawable.sputnik),
        painterResource(id = R.drawable.sputnik),
        painterResource(id = R.drawable.sputnik)
    )
    val painterFiveUsers = listOf(
        painterResource(id = R.drawable.sputnik),
        painterResource(id = R.drawable.sputnik),
        painterResource(id = R.drawable.sputnik),
        painterResource(id = R.drawable.sputnik),
        painterResource(id = R.drawable.sputnik)
    )
    Column {
        Box(modifier = Modifier.padding(16.dp)) {
            Pills(painterOneUser)
        }
        Box(modifier = Modifier.padding(16.dp)) {
            Pills(painterTwoUsers)
        }
        Box(modifier = Modifier.padding(16.dp)) {
            Pills(painterThreeUsers)
        }
        Box(modifier = Modifier.padding(16.dp)) {
            Pills(painterFourUsers)
        }
        Box(modifier = Modifier.padding(16.dp)) {
            Pills(painterFiveUsers)
        }
    }

}

@Composable
fun OverlappingRow(
    modifier: Modifier = Modifier,
    @FloatRange(from = 0.1, to = 1.0) overlapFactor: Float = 0.5f,
    content: @Composable () -> Unit,
) {
    val measurePolicy = overlappingRowMeasurePolicy(overlapFactor)
    Layout(
        measurePolicy = measurePolicy,
        content = content,
        modifier = modifier
    )
}

fun overlappingRowMeasurePolicy(overlapFactor: Float) = MeasurePolicy { measurables, constraints ->
    val placeables = measurables.map { measurable -> measurable.measure(constraints) }
    val height = placeables.maxOf { it.height }
    val width = (placeables.subList(1, placeables.size)
        .sumOf { it.width } * overlapFactor + placeables[0].width).toInt()
    layout(width, height) {
        var xPos = 0
        for (placeable in placeables) {
            placeable.placeRelative(xPos, 0, 0f)
            xPos += (placeable.width * overlapFactor).toInt()
        }
    }
}


