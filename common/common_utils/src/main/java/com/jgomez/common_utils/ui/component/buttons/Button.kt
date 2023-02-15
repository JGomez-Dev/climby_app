package com.jgomez.common_utils.ui.component.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jgomez.common_utils.ui.theme.ClimbyColor
import com.jgomez.common_utils.ui.theme.ClimbyTheme
import com.jgomez.common_utils.ui.theme.Padding


@Composable
fun Button(
    state: ButtonState = ButtonState.Active,
    title: String,
    subTitle: String? = null,
    theme: ClimbyTheme = ClimbyTheme(),
    notificationsNumber: Int? = null,
    color: ClimbyColor = ClimbyColor(),
    onClick: () -> Unit,
) {
    Box()
    {
        Box()
        {
            Surface(
                shape = CircleShape, modifier = Modifier.clickable { onClick() }
            ) {
                Row(
                    modifier = Modifier
                        .background(setBackgroundColor(state), CircleShape)
                ) {
                    Row {
                        Column(
                            modifier = Modifier
                                .padding(vertical = if (subTitle != null) theme.padding.padding01 else theme.padding.padding03)
                                .fillMaxWidth()
                        ) {
                            Text(
                                modifier = Modifier
                                    .align(CenterHorizontally),
                                text = title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = getTitleColor(state),
                            )
                            if (subTitle != null) {
                                Text(
                                    modifier = Modifier
                                        .align(CenterHorizontally),
                                    text = subTitle,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 13.sp,
                                    color = getSubtitleColor(state),
                                )
                            }
                        }

                    }
                }
            }
        }
        if (notificationsNumber != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
            ) {
                BubbleNotification(color, notificationsNumber)
            }
        }
    }
}

@Composable
private fun BubbleNotification(
    color: ClimbyColor,
    notificationsNumber: Int?
) {
    Text(
        modifier = Modifier
            .size(20.dp)
            .offset((0).dp, (-3).dp)
            .background(shape = CircleShape, color = color.accent),
        textAlign = TextAlign.Center,
        text = notificationsNumber.toString(),
        fontSize = 14.sp,
        style = MaterialTheme.typography.body1.merge(),
        color = color.white,
    )
}

private fun getSubtitleColor(state: ButtonState, theme: ClimbyTheme = ClimbyTheme()): Color {
    return when (state) {
        is ButtonState.Active -> {
            theme.color.n500
        }

        is ButtonState.Inactive -> {
            theme.color.n600
        }

        is ButtonState.Disable -> {
            theme.color.n200
        }
    }
}

private fun getTitleColor(state: ButtonState, theme: ClimbyTheme = ClimbyTheme()): Color {
    return when (state) {
        is ButtonState.Inactive -> {
            theme.color.black
        }

        else -> {
            theme.color.white
        }
    }
}

private fun setBackgroundColor(state: ButtonState, theme: ClimbyTheme = ClimbyTheme()): Color {
    return when (state) {
        is ButtonState.Active -> {
            theme.color.black
        }

        is ButtonState.Inactive -> {
            theme.color.n200
        }

        is ButtonState.Disable -> {
            theme.color.n500
        }
    }
}

private fun txColor(enable: Boolean): Color {
    return if (enable) ClimbyColor().n500 else ClimbyColor().n200
}

@Composable
@Preview
fun ButtonPreview() {
    val notificationsNumber = 3
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Text(
            text = "BUTTONS",
            modifier = Modifier.align(CenterHorizontally)
        )
        Row {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(all = Padding().padding02)
            ) {
                Button(
                    state = ButtonState.Inactive,
                    title = "Title",
                    onClick = {})
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(all = Padding().padding02)
            ) {
                Button(
                    state = ButtonState.Inactive,
                    title = "Title",
                    subTitle = "Subtitle",
                    onClick = {},
                )
            }
        }
        Row {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(all = Padding().padding02)
            ) {
                Button(
                    title = "Title",
                    state = ButtonState.Inactive,
                    notificationsNumber = notificationsNumber,
                    onClick = {},
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(all = Padding().padding02)
            ) {
                Button(
                    title = "Title",
                    subTitle = "Subtitle",
                    state = ButtonState.Inactive,
                    notificationsNumber = notificationsNumber,
                    onClick = {},
                )
            }
        }
        Row {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(all = Padding().padding02)
            ) {
                Button(title = "Title", onClick = {})
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(all = Padding().padding02)
            ) {
                Button(
                    title = "Title",
                    subTitle = "Subtitle",
                    onClick = {},
                )
            }
        }
        Row {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(all = Padding().padding02)
            ) {
                Button(title = "Title", onClick = {}, notificationsNumber = notificationsNumber)
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(all = Padding().padding02)
            ) {
                Button(
                    title = "Title",
                    subTitle = "Subtitle",
                    notificationsNumber = notificationsNumber,
                    onClick = {},
                )
            }
        }
        Row {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(all = Padding().padding02)
            ) {
                Button(
                    title = "Title",
                    onClick = {},
                    state = ButtonState.Disable,
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(all = Padding().padding02)
            ) {
                Button(
                    title = "Title",
                    subTitle = "Subtitle",
                    onClick = {},
                    state = ButtonState.Disable
                )
            }
        }
    }
}

@Immutable
sealed interface ButtonState {
    @Immutable
    object Active : ButtonState

    @Immutable
    object Inactive : ButtonState

    @Immutable
    object Disable : ButtonState
}
