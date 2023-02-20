package com.jgomez.authentication_presentacion.views.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.jgomez.common_utils.R
import com.jgomez.common_utils.ui.component.buttons.MenuButton
import com.jgomez.common_utils.ui.component.buttons.MenuButtonType
import com.jgomez.common_utils.ui.component.forms.TextField
import com.jgomez.common_utils.ui.component.toolbar.TopBar
import com.jgomez.common_utils.ui.theme.ClimbyTheme
import com.jgomez.common_utils.ui.theme.Shapes
import com.jgomez.common_utils.ui.wrapper.ClimbyImage
import com.jgomez.authentication_presentacion.R.string as RC

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OnBoardingTwoContent(
    onClick: () -> Unit,
    theme: ClimbyTheme = ClimbyTheme(),
    photoUrl: String,
    name: String
) {

    var acceptedShare by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ClimbyTheme().color.n100)
            .fillMaxHeight()
    ) {
        TopBar(
            ClimbyImage.Resource(id = R.drawable.arrow_back),
            "¿Tu información es correcta?",
            onClick = onClick
        )
        Spacer(modifier = Modifier.padding(bottom = 16.dp))
        Box(
            modifier = Modifier
                .align(CenterHorizontally), contentAlignment = Center
        ) {
            Image(
                painter = rememberAsyncImagePainter(photoUrl),
                contentDescription = "_image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
            )
        }
        Spacer(modifier = Modifier.padding(bottom = 32.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(), contentAlignment = Center
        ) {
            Text(
                text = "Cambiar foto",
                fontSize = 16.sp,
                lineHeight = 16.sp,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            )
        }
        Spacer(modifier = Modifier.padding(bottom = 24.dp))
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = "Nombre",
                fontSize = 16.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = theme.color.n600
            )
            Spacer(modifier = Modifier.padding(bottom = 8.dp))
            TextField(title = name, placeholder = "Nombre")
        }
        Spacer(modifier = Modifier.padding(bottom = 32.dp))
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = stringResource(RC.text_phone),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = theme.color.n600
            )
            Spacer(modifier = Modifier.padding(bottom = 8.dp))
            Row {
                Box(modifier = Modifier.weight(1f)) {
                    TextField(
                        title = "+34",
                        placeholder = "DD/MM",
                        icon = ClimbyImage.Resource(id = R.drawable.arrow_down)
                    )
                }

                Spacer(modifier = Modifier.padding(start = 16.dp))
                Box(modifier = Modifier.weight(2f)) {
                    TextField(
                        title = "",
                        placeholder = stringResource(RC.text_phone)
                    )
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Box {
                CompositionLocalProvider(LocalMinimumTouchTargetEnforcement provides false) {
                    Checkbox(
                        checked = acceptedShare,
                        colors = CheckboxDefaults.colors(
                            checkedColor = theme.color.accent,
                            uncheckedColor = Color.Transparent
                        ),
                        onCheckedChange = { acceptedShare = it },
                        modifier = Modifier
                            .padding(top = 32.dp, start = 32.dp, end = 19.dp)
                            .border(
                                BorderStroke(
                                    1.dp,
                                    color = if (acceptedShare) theme.color.accent else theme.color.n300
                                ), shape = Shapes.medium
                            )
                            .size(24.dp)
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 24.dp),
                contentAlignment = BottomStart
            ) {
                Text(
                    text = stringResource(RC.text_join_phone),
                    fontSize = 14.sp, color = theme.color.n600,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 20.sp
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(all = 16.dp), contentAlignment = BottomCenter
        ) {
            MenuButton(
                state = MenuButtonType.Active,
                text = "Continuar",
                onClick = {}
            )
        }
    }

}
