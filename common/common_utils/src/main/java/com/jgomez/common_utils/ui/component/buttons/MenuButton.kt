package com.jgomez.common_utils.ui.component.buttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jgomez.common_utils.R
import androidx.compose.material.Button
import androidx.compose.runtime.setValue
import com.jgomez.common_utils.ui.theme.ClimbyTheme
import com.jgomez.common_utils.ui.theme.text.ClimbyTextStyle
import com.jgomez.common_utils.ui.wrapper.ClimbyImage
import com.jgomez.common_utils.ui.wrapper.painter

@Composable
fun MenuButton(
    state: MenuButtonType = MenuButtonType.Disabled,
    text: String,
    onClick: () -> Unit,
    icon: Painter? = null,
    theme: ClimbyTheme = ClimbyTheme(),
    onStateChanged: (() -> MenuButtonType)? = null,
) {
    val mutableState: MenuButtonType by remember { mutableStateOf(state) }
    val finalState = onStateChanged?.invoke() ?: mutableState

    Box {
        Row {
            Button(
                modifier = Modifier
                    .clip(CircleShape)
                    .fillMaxWidth(),
                shape = CircleShape,
                onClick = { onClick() },
                enabled = checkEnabled(finalState),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = setBackgroundColor(finalState),
                    disabledBackgroundColor = theme.color.n500
                )
            ) {
                Text(
                    modifier = Modifier
                        .align(CenterVertically)
                        .padding(vertical = 8.dp),
                    text = text,
                    style = ClimbyTextStyle.Heading4(),
                    color = getTitleColor(finalState),
                )
            }
        }
        if (icon != null) {
            Image(
                painter = icon,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(24.dp)
                    .align(Alignment.CenterStart),
                contentDescription = null,
            )
        }
    }
}

fun checkEnabled(mutableState: MenuButtonType): Boolean =
    mutableState == MenuButtonType.Active || mutableState == MenuButtonType.Login || mutableState == MenuButtonType.Whatsapp || mutableState == MenuButtonType.Disabled

private fun setBackgroundColor(state: MenuButtonType, theme: ClimbyTheme = ClimbyTheme()): Color {
    return when (state) {
        is MenuButtonType.Active -> {
            theme.color.black
        }

        is MenuButtonType.Disabled -> {
            theme.color.n200
        }

        is MenuButtonType.Login -> {
            theme.color.white
        }

        is MenuButtonType.Whatsapp -> {
            theme.color.buttonWhatsApp
        }

        is MenuButtonType.Inactive -> {
            theme.color.n500
        }
    }
}

private fun getTitleColor(state: MenuButtonType, theme: ClimbyTheme = ClimbyTheme()): Color {
    return when (state) {
        is MenuButtonType.Active, MenuButtonType.Whatsapp, MenuButtonType.Inactive -> {
            theme.color.white
        }

        else -> {
            theme.color.black
        }
    }
}

@Composable
@Preview
fun MenuButtonPreview() {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        MenuButton(onClick = {}, state = MenuButtonType.Active, text = "Continuar")
        MenuButton(onClick = {}, state = MenuButtonType.Disabled, text = "Cerrar sesión")
        MenuButton(
            onClick = {}, state = MenuButtonType.Login, text = "Entrar con Facebook",
            icon = ClimbyImage.Resource(
                R.drawable.facebook
            ).painter,
        )
        MenuButton(
            onClick = {}, state = MenuButtonType.Login, text = "Entrar con Google",
            icon = ClimbyImage.Resource(
                R.drawable.google
            ).painter,
        )

        MenuButton(
            onClick = {}, state = MenuButtonType.Inactive, text = "Continuar"
        )

    }
}

@Immutable
sealed interface MenuButtonType {
    @Immutable
    object Active : MenuButtonType

    @Immutable
    object Disabled : MenuButtonType

    @Immutable
    object Inactive : MenuButtonType


    @Immutable
    object Login : MenuButtonType

    @Immutable
    object Whatsapp : MenuButtonType
}
