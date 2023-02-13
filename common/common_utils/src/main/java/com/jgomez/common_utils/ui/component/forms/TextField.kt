package com.jgomez.common_utils.ui.component.forms

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jgomez.common_utils.R
import com.jgomez.common_utils.ui.theme.ClimbyTheme
import com.jgomez.common_utils.ui.wrapper.ClimbyImage
import com.jgomez.common_utils.ui.wrapper.painter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextField(
    theme: ClimbyTheme = ClimbyTheme(),
    title: String,
    placeholder: String = "",
    icon: ClimbyImage? = null,
    singleLine: Boolean = true
) {
    var name by remember {
        mutableStateOf(title)
    }
    TextField(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RectangleShape,
        value = name,
        onValueChange = { name = it },
        singleLine = singleLine,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = theme.color.white,
            focusedIndicatorColor = Color.Transparent, //hide the indicator
            unfocusedIndicatorColor = Color.Transparent
        ),
        placeholder = { Text(text = placeholder, fontSize = 16.sp, color = theme.color.n300) },
        trailingIcon = {
            if (icon != null)
                Image(painter = icon.painter, contentDescription = "")
        }
    )
}


@Composable
@Preview
fun EditTextPreview() {
    Column() {
        TextField(
            title = "Albarracín",
            placeholder = "Nombre"
        )
        Spacer(modifier = Modifier.padding(top = 16.dp))
        TextField(
            title = "",
            placeholder = "Elige tu provincia",
            icon = ClimbyImage.Resource(id = R.drawable.arrow_down)
        )
        Spacer(modifier = Modifier.padding(top = 16.dp))
        TextField(
            title = "",
            placeholder = "Boulder, Clásica, Deportiva...",
            icon = ClimbyImage.Resource(id = R.drawable.arrow_down)
        )
        Spacer(modifier = Modifier.padding(top = 16.dp))
        Row() {
            Box(modifier = Modifier.weight(1f)) {
                TextField(
                    title = "",
                    placeholder = "DD/MM",
                    icon = ClimbyImage.Resource(id = R.drawable.calendar)
                )
            }

            Spacer(modifier = Modifier.padding(start = 16.dp))
            Box(modifier = Modifier.weight(1f)) {
                TextField(
                    title = "",
                    placeholder = "0",
                    icon = ClimbyImage.Resource(id = R.drawable.arrow_down)
                )
            }
        }
    }
}