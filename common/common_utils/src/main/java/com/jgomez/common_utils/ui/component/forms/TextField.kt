package com.jgomez.common_utils.ui.component.forms

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jgomez.common_utils.R
import com.jgomez.common_utils.ui.theme.ClimbyTheme
import com.jgomez.common_utils.ui.theme.text.ClimbyTextStyle
import com.jgomez.common_utils.ui.wrapper.ClimbyImage
import com.jgomez.common_utils.ui.wrapper.painter

const val PHONE_LENGTH = 9
const val mask = "xx xxx xx xx"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextField(
    type: TextInputType? = TextInputType.Phone,
    theme: ClimbyTheme = ClimbyTheme(),
    title: String = "",
    placeholder: String = "",
    icon: ClimbyImage? = null,
    singleLine: Boolean = true,
    onTextChanged: ((String) -> String)? = null
) {
    var mutableText by remember { mutableStateOf(title) }

    TextField(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RectangleShape,
        value = mutableText,
        onValueChange = {
            val finalString = onTextChanged?.invoke(it) ?: it
            if (type != TextInputType.Phone) {
                mutableText = finalString
            } else if (finalString.length <= PHONE_LENGTH)
                mutableText = finalString
        },
        textStyle = ClimbyTextStyle.Heading5Value(),
        singleLine = singleLine,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = theme.color.white,
            focusedIndicatorColor = Color.Transparent, //hide the indicator
            unfocusedIndicatorColor = Color.Transparent
        ),
        placeholder = {
            Text(
                text = placeholder,
                style = ClimbyTextStyle.Heading5Value(),
                color = theme.color.n400
            )
        },
        keyboardOptions = if (type == TextInputType.Phone) KeyboardOptions(keyboardType = KeyboardType.Number) else KeyboardOptions.Default,
        trailingIcon = if (icon != null) {
            { Image(painter = icon.painter, contentDescription = "") }
        } else null,
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

@Immutable
sealed interface TextInputType {

    @Immutable
    object Text : TextInputType

    @Immutable
    object Phone : TextInputType
}