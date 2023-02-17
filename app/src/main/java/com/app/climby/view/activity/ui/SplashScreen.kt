package com.app.climby.view.activity.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.navigation.NavHostController
import com.jgomez.authentication_presentacion.R
import com.app.climby.view.activity.ui.graph.AuthScreen
import com.jgomez.common_utils.ui.component.buttons.Button
import com.jgomez.common_utils.ui.theme.ClimbyTheme
import kotlinx.coroutines.delay


@Composable
fun AnimatedSplashScreen(navController: NavHostController) {
    LaunchedEffect(key1 = true) {
        delay(1000)
        navController.popBackStack()
        navController.navigate(AuthScreen.Login.route)
    }
    Splash()
}


@Composable
fun Splash(theme: ClimbyTheme = ClimbyTheme()) {
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
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(start = 16.dp, end = 16.dp, bottom = 44.dp)
            ) {
                Box {
                    Buttons(onForgotClick = {}, onSignUpClick = {})
                }
            }
        }
    }

}

@Composable
private fun Buttons(
    theme: ClimbyTheme = ClimbyTheme(),
    onSignUpClick: () -> Unit,
    onForgotClick: () -> Unit
) {
    var visible by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = true) {
        delay(1000)
        visible = !visible
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(initialAlpha = 0.4f),
        exit = fadeOut(animationSpec = tween(durationMillis = 1)),
        content = {
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
                    title = "Entrar con facebook"
                ) { onSignUpClick() }
                Spacer(modifier = Modifier.padding(bottom = 16.dp))
                Button(
                    title = "Entrar con Google"
                ) {
                    onForgotClick()
                }
            }
        }
    )
}
@Composable
@Preview(showSystemUi = true)
fun SplashScreenView() {
    Splash()
}
