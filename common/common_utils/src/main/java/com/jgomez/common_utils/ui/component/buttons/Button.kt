package com.jgomez.common_utils.ui.component.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jgomez.common_utils.R
import com.jgomez.common_utils.ui.theme.ClimbyColor
import com.jgomez.common_utils.ui.theme.Padding
import com.jgomez.common_utils.ui.wrapper.ClimbyImage
import com.jgomez.common_utils.ui.wrapper.painter


@Composable
fun Button(
    type: ButtonType,
    title: String,
    subTitle: String? = null,
    textPadding: Dp = 16.dp,
    enable: Boolean = true,
    notificationsNumber: Int? = null,
    icon: Painter? = null,
    fontSize: TextUnit = 14.sp,
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
                        .background(type.bgColor(color), CircleShape)
                ) {
                    Row {
                        Column(
                            modifier = Modifier
                                .padding(vertical = textPadding)
                                .fillMaxWidth()
                        ) {
                            Text(
                                modifier = Modifier
                                    .align(CenterHorizontally),

                                text = title,
                                fontSize = type.fontSize(fontSize),
                                style = MaterialTheme.typography.button.merge(),
                                color = type.textColor(color),
                            )
                            if (subTitle != null) {
                                Text(
                                    modifier = Modifier
                                        .align(CenterHorizontally),
                                    text = type.textSubtitle(subTitle)!!,
                                    style = MaterialTheme.typography.subtitle1.merge(),
                                    color = txColor(enable),
                                )
                            }
                        }

                    }
                }
            }
        }
        if (icon != null) {
            Image(
                painter = icon,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(24.dp)
                    .align(CenterStart),
                contentDescription = null,
            )
        }
        if (type.notification(notificationsNumber != null) && enable) {
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
            .size(24.dp)
            .offset((6).dp, (-6).dp)
            .background(shape = CircleShape, color = color.accent)
            .border(BorderStroke(2.dp, color = color.n100), CircleShape),
        textAlign = TextAlign.Center,
        text = notificationsNumber.toString(),
        fontSize = 14.sp,
        style = MaterialTheme.typography.body1.merge(),
        color = color.white,
    )
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
                    type = ButtonType.Enable,
                    title = "Title",
                    onClick = {})
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(all = Padding().padding02)
            ) {
                Button(
                    type = ButtonType.EnableWithSubtitle,
                    title = "Title",
                    subTitle = "Subtitle",
                    onClick = {},
                    textPadding = 8.dp
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
                    title = "Title", notificationsNumber = notificationsNumber, onClick = {},
                    type = ButtonType.EnableWithNotification
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
                    notificationsNumber = notificationsNumber,
                    textPadding = 8.dp,
                    onClick = {},
                    type = ButtonType.EnableWithSubtitleWithNotification
                )
            }
        }
        Row {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(all = Padding().padding02)
            ) {
                Button(title = "Title", enable = false, onClick = {}, type = ButtonType.Disable)
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(all = Padding().padding02)
            ) {
                Button(
                    title = "Title",
                    subTitle = "Subtitle",
                    textPadding = 8.dp,
                    enable = false,
                    onClick = {},
                    type = ButtonType.DisableWithSubtitle
                )
            }
        }
        Text(
            text = "EXAMPLES",
            modifier = Modifier.align(CenterHorizontally)
        )
        Box(
            modifier = Modifier.padding(all = Padding().padding02)
        ) {
            Button(
                icon = ClimbyImage.Resource(R.drawable.google).painter,
                type = ButtonType.EnableSocial,
                title = "Entrar con Google",
                textPadding = 20.dp,
                onClick = {})
        }
        Box(
            modifier = Modifier.padding(all = Padding().padding02)
        ) {
            Button(
                icon = ClimbyImage.Resource(R.drawable.facebook).painter,
                type = ButtonType.EnableSocial,
                title = "Entrar con Facebook",
                textPadding = 20.dp,
                onClick = {})
        }
        Box(
            modifier = Modifier.padding(all = Padding().padding02)
        ) {
            Button(
                title = "Ver peticiones",
                onClick = {},
                textPadding = 8.dp,
                type = ButtonType.Enable
            )
        }
        Box(
            modifier = Modifier.padding(all = Padding().padding02)
        ) {
            Button(
                title = "Ver peticiones",
                textPadding = 8.dp,
                subTitle = "2 plazas",
                onClick = {}, type = ButtonType.EnableWithSubtitle
            )
        }
        Box(
            modifier = Modifier.padding(all = Padding().padding02)
        ) {
            Button(title = "Solicitado", textPadding = 8.dp, onClick = {}, type = ButtonType.Enable)
        }
        Box(
            modifier = Modifier.padding(all = Padding().padding02)
        ) {
            Button(
                title = "Te has unido",
                textPadding = 8.dp,
                subTitle = "Liberar plaza",
                onClick = {}, type = ButtonType.EnableWithSubtitle
            )
        }
        Box(
            modifier = Modifier.padding(all = Padding().padding02)
        ) {
            Button(
                title = "No disponible",
                enable = false,
                textPadding = 8.dp,
                subTitle = "Ya no existe",
                onClick = {}, type = ButtonType.DisableWithSubtitle
            )
        }
        Box(
            modifier = Modifier.padding(all = Padding().padding02)
        ) {
            Button(
                title = "Rechazado",
                subTitle = "Ver mensaje",
                textPadding = 8.dp,
                notificationsNumber = 1,
                onClick = {}, type = ButtonType.EnableWithSubtitleWithNotification
            )
        }
        Box(
            modifier = Modifier.padding(all = Padding().padding02)
        ) {
            Button(
                title = "Terminado",
                subTitle = "Ver resumen",
                textPadding = 8.dp,
                notificationsNumber = 1,
                onClick = {}, type = ButtonType.EnableWithSubtitleWithNotification
            )
        }
        Box(
            modifier = Modifier.padding(all = Padding().padding02)
        ) {
            Button(
                title = "Continuar",
                onClick = {},
                textPadding = 20.dp,
                type = ButtonType.Disable
            )
        }
        Box(
            modifier = Modifier.padding(all = Padding().padding02)
        ) {
            Button(
                title = "Cerrar sessión",
                enable = false,
                onClick = {},
                textPadding = 16.dp,
                type = ButtonType.Secondary
            )
        }
    }
}


@Immutable
sealed class ButtonType(
    open val bgColor: (color: ClimbyColor) -> Color,
    open val textColor: (color: ClimbyColor) -> Color,
    open val textSubtitle: (text: String?) -> String?,
    open val fontSize: (fontSize: TextUnit) -> TextUnit,
    open val notification: (notification: Boolean) -> Boolean
) {
    @Immutable
    object Enable : ButtonType(
        bgColor = { it.black },
        textColor = { it.white },
        fontSize = { 14.sp },
        textSubtitle = { null },
        notification = { false }
    )

    @Immutable
    object EnableWithSubtitle : ButtonType(
        bgColor = { it.black },
        textColor = { it.white },
        fontSize = { 14.sp },
        textSubtitle = { it },
        notification = { false }
    )

    @Immutable
    object EnableWithNotification : ButtonType(
        bgColor = { it.black },
        textColor = { it.white },
        fontSize = { 14.sp },
        textSubtitle = { null },
        notification = { true }
    )

    @Immutable
    object EnableWithSubtitleWithNotification : ButtonType(
        bgColor = { it.black },
        textColor = { it.white },
        fontSize = { 14.sp },
        textSubtitle = { it },
        notification = { true }
    )

    @Immutable
    object Disable : ButtonType(
        bgColor = { it.n500 },
        textColor = { it.white },
        fontSize = { 14.sp },
        textSubtitle = { null },
        notification = { false }
    )

    @Immutable
    object DisableWithSubtitle : ButtonType(
        bgColor = { it.n500 },
        textColor = { it.white },
        fontSize = { 14.sp },
        textSubtitle = { it },
        notification = { false }
    )

    @Immutable
    object EnableSocial : ButtonType(
        bgColor = { it.white },
        textColor = { it.black },
        fontSize = { 18.sp },
        textSubtitle = { null },
        notification = { false }
    )

    @Immutable
    object Secondary : ButtonType(
        bgColor = { it.n200 },
        textColor = { it.black },
        fontSize = { 14.sp },
        textSubtitle = { null },
        notification = { false }
    )
    @Immutable
    object Contact : ButtonType(
        bgColor = { it.colorButtonWhatsApp },
        textColor = { it.white },
        fontSize = { 14.sp },
        textSubtitle = { null },
        notification = { false }
    )
}
