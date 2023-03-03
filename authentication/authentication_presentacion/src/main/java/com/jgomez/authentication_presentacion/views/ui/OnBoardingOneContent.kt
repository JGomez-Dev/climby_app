package com.jgomez.authentication_presentacion.views.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.jgomez.common_utils.R
import com.jgomez.common_utils.ui.component.buttons.MenuButton
import com.jgomez.common_utils.ui.component.buttons.MenuButtonType
import com.jgomez.common_utils.ui.component.forms.TextField
import com.jgomez.common_utils.ui.component.forms.TextInputType
import com.jgomez.common_utils.ui.component.toolbar.TopBar
import com.jgomez.common_utils.ui.theme.ClimbyTheme
import com.jgomez.common_utils.ui.theme.Shapes
import com.jgomez.common_utils.ui.theme.text.ClimbyTextStyle
import com.jgomez.common_utils.ui.wrapper.ClimbyImage
import com.jgomez.authentication_presentacion.R.string as RC

const val LONG_PHONE_NUMBER = 9

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OnBoardingOneContent(
    theme: ClimbyTheme = ClimbyTheme(),
    photoUrl: String,
    name: String,
    onClickBack: () -> Unit,
    onClickButton: (phone: String) -> Unit,

) {
    var acceptedShare by remember { mutableStateOf(false) }
    var mutablePhone: String by remember { mutableStateOf("") }
    var mutableName: String by remember { mutableStateOf(name) }
    var mutableState: MenuButtonType by remember { mutableStateOf(MenuButtonType.Inactive) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ClimbyTheme().color.n100)
            .fillMaxHeight()
    ) {
        TopBar(
            ClimbyImage.Resource(id = R.drawable.arrow_back),
            "¿Tu información es correcta?",
            onClick = onClickBack
        )
        Spacer(modifier = Modifier.padding(bottom = 16.dp))
        Box(
            modifier = Modifier.align(CenterHorizontally), contentAlignment = Center
        ) {
            AsyncImage(
                model = photoUrl,
                contentDescription = "_image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
            )
        }
        Spacer(modifier = Modifier.padding(bottom = 32.dp))
        Box(
            modifier = Modifier.fillMaxWidth(), contentAlignment = Center
        ) {
            Text(
                modifier = Modifier.clickable { },
                text = "Cambiar foto",
                style = ClimbyTextStyle.Heading5Link(),
                textDecoration = TextDecoration.Underline,
            )
        }
        Spacer(modifier = Modifier.padding(bottom = 24.dp))
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = "Nombre", color = theme.color.n600, style = ClimbyTextStyle.Heading5Label()
            )
            Spacer(modifier = Modifier.padding(bottom = 8.dp))
            TextField(title = mutableName, placeholder = "Nombre", type = TextInputType.Text) {
                mutableName = it
                it
            }
        }
        Spacer(modifier = Modifier.padding(bottom = 32.dp))
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = stringResource(RC.text_phone),
                color = theme.color.n600,
                style = ClimbyTextStyle.Heading5Label()
            )
            Spacer(modifier = Modifier.padding(bottom = 8.dp))
            Row {
                Box(modifier = Modifier.weight(1f)) {
                    TextField(
                        title = "+34",
                        placeholder = "DD/MM",
                        type = TextInputType.Text,
                        icon = ClimbyImage.Resource(id = R.drawable.arrow_down)
                    )
                }
                Spacer(modifier = Modifier.padding(start = 16.dp))
                Box(modifier = Modifier.weight(2f)) {
                    TextField(
                        title = mutablePhone,
                        placeholder = stringResource(RC.text_phone),
                        type = TextInputType.Phone
                    ) {
                        mutablePhone = it
                        it
                    }
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Box {
                CompositionLocalProvider(LocalMinimumTouchTargetEnforcement provides false) {
                    Checkbox(
                        checked = acceptedShare,
                        colors = CheckboxDefaults.colors(
                            checkedColor = theme.color.accent, uncheckedColor = Color.Transparent
                        ),
                        onCheckedChange = { acceptedShare = it },
                        modifier = Modifier
                            .padding(top = 32.dp, start = 32.dp, end = 24.dp)
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
                    text = stringResource(RC.onboarding_label_join_phone),
                    color = theme.color.n600,
                    style = ClimbyTextStyle.Heading6Body(),
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(all = 16.dp),
            contentAlignment = BottomCenter
        ) {
            MenuButton(
                state = mutableState,
                text = "Continuar",
                onClick = { onClickButton(mutablePhone) }) {
                if (mutablePhone.isNotBlank() && mutableName.isNotBlank() && acceptedShare && mutablePhone.length == LONG_PHONE_NUMBER) {
                    mutableState = MenuButtonType.Active
                    MenuButtonType.Active
                } else {
                    mutableState = MenuButtonType.Inactive
                    MenuButtonType.Inactive
                }

            }
        }
    }
}

@Composable
@Preview
fun OnBoardingContentPreview(
) {
    OnBoardingOneContent(
        photoUrl = "",
        name = "",
        onClickBack = { /*TODO*/ },
        onClickButton = {}
    )
}