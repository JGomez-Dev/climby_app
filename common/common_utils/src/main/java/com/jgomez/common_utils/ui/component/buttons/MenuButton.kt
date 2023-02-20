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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jgomez.common_utils.R
import com.jgomez.common_utils.ui.theme.ClimbyTheme
import com.jgomez.common_utils.ui.wrapper.ClimbyImage
import com.jgomez.common_utils.ui.wrapper.painter

@Composable
fun MenuButton(
    state: MenuButtonType = MenuButtonType.Active,
    text: String,
    onClick: () -> Unit,
    icon: Painter? = null,
) {
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
                            .padding(vertical = 20.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally),
                            text = text,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = getTitleColor(state),
                        )
                    }
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
}

private fun setBackgroundColor(state: MenuButtonType, theme: ClimbyTheme = ClimbyTheme()): Color {
    return when (state) {
        is MenuButtonType.Active -> {
            theme.color.black
        }

        is MenuButtonType.Inactive -> {
            theme.color.n200
        }

        is MenuButtonType.Login -> {
            theme.color.white
        }

        is MenuButtonType.Whatsapp -> {
            theme.color.buttonWhatsApp
        }
    }
}

private fun getTitleColor(state: MenuButtonType, theme: ClimbyTheme = ClimbyTheme()): Color {
    return when (state) {
        is MenuButtonType.Active, MenuButtonType.Whatsapp -> {
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
        MenuButton(onClick = {}, state = MenuButtonType.Inactive, text = "Cerrar sesión")
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

    }
}

@Immutable
sealed interface MenuButtonType {
    @Immutable
    object Active : MenuButtonType

    @Immutable
    object Inactive : MenuButtonType

    @Immutable
    object Login : MenuButtonType

    @Immutable
    object Whatsapp : MenuButtonType
}
