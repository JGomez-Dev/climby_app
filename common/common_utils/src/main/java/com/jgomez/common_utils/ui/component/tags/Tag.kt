package com.jgomez.common_utils.ui.component.tags

import android.view.RoundedCorner
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jgomez.common_utils.ui.theme.ClimbyColor
import com.jgomez.common_utils.ui.theme.Padding
import com.jgomez.common_utils.ui.theme.text.ClimbyTextStyle


@Composable
fun Tag(
    text: String,
    color: ClimbyColor = ClimbyColor(),
    onClick: () -> Unit,
    selectedValue: MutableState<String>
) {
    Surface(
        modifier = Modifier
            .clip(CircleShape)
            .clickable {
                selectedValue.value = text
                onClick()
            }
    ) {
        Box(
            modifier = Modifier
                .background(
                    if (selectedValue.value == text) color.black else color.n200,
                    CircleShape
                ),
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 12.dp),
                text = text,
                style = ClimbyTextStyle.Heading6(),
                color = (if (selectedValue.value == text) color.white else color.black),
            )
        }
    }


}

@Composable
@Preview
fun TagPreview() {

    Column {
        val button = remember { mutableStateOf("Todas") }
        Tag(
            text = "Todas",
            onClick = { button.value = "Ninguna" },
            selectedValue = button
        )
        Spacer(modifier = Modifier.padding(Padding().padding01))
        val button1 = remember { mutableStateOf("Ninguna") }
        Tag(
            text = "Todas",
            onClick = { button1.value = "Ninguna" },
            selectedValue = button1
        )
    }
}