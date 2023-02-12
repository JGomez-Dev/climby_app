package com.jgomez.authentication_presentacion.views.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jgomez.authentication_presentacion.R
import com.jgomez.common_utils.ui.component.buttons.Button
import com.jgomez.common_utils.ui.component.buttons.ButtonType
import com.jgomez.common_utils.ui.theme.ClimbyTheme

@Composable
fun LoginContent(
    onClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onForgotClick: () -> Unit,
    theme: ClimbyTheme = ClimbyTheme()
) {
    Column {
        Box {
            Image(
                modifier = Modifier.padding(bottom = 0.dp),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.splash_screen),
                contentDescription = "Splash Image Background"
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .size(50.dp)
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Transparent, Color.Black)
                        )
                    )
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .size(144.dp)
                .background(
                    theme.color.black
                )
        ) {}
    }
    Column {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            Column(modifier = Modifier.align(Alignment.Center)) {
                Image(
                    modifier = Modifier.padding(top = 40.dp),
                    painter = painterResource(id = R.drawable.splash_climby),
                    contentDescription = "Splash Image Text"
                )
            }
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(start = 16.dp, end = 16.dp, bottom = 44.dp)
            ) {
                Box {
                    Buttons(onSignUpClick = onClick, onForgotClick = onForgotClick)
                }
            }
        }
    }

    /*Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.clickable { onClick() },
            text = "LOGIN",
            fontSize = MaterialTheme.typography.h3.fontSize,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier.clickable { onSignUpClick() },
            text = "On Boarding",
            fontSize = MaterialTheme.typography.body1.fontSize,
            fontWeight = FontWeight.Medium
        )
        Text(
            modifier = Modifier.clickable { onForgotClick() },
            text = "Forgot Password",
            fontSize = MaterialTheme.typography.body1.fontSize,
            fontWeight = FontWeight.Medium
        )
    }*/
}

@Composable
private fun Buttons(
    theme: ClimbyTheme = ClimbyTheme(),
    onSignUpClick: () -> Unit,
    onForgotClick: () -> Unit
) {
    Column {
        Text(
            text = "Únete a otros escaladores como tú y sal a escalar de forma segura",
            color = theme.color.white,
            modifier = Modifier.padding(
                start = 43.dp,
                end = 43.dp,
                bottom = 56.dp
            ),
            textAlign = TextAlign.Center,
            fontSize = 18.sp
        )
        Button(
            type = ButtonType.EnableSocial,
            title = "Entrar con facebook",
            icon = painterResource(id = R.drawable.facebook),
            textPadding = 20.dp
        ) { onSignUpClick() }
        Spacer(modifier = Modifier.padding(bottom = 16.dp))
        Button(
            type = ButtonType.EnableSocial,
            title = "Entrar con Google",
            icon = painterResource(id = R.drawable.google),
            textPadding = 20.dp
        ) {
            onForgotClick()
        }
    }
}

@Composable
@Preview
fun LoginContentPreview(
) {
    LoginContent({}, {}, {})
}