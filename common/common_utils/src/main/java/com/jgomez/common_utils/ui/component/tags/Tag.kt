package com.jgomez.common_utils.ui.component.tags

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jgomez.common_utils.ui.theme.ClimbyColor
import com.jgomez.common_utils.ui.theme.Padding

@Composable
fun Tag(
    text: String,
    selected: Boolean = false,
    color: ClimbyColor = ClimbyColor(),
    onClick: () -> Unit
) {
    var selectedItem by remember { mutableStateOf(selected) }
    Surface(
        shape = CircleShape,
        color = (if (selectedItem) color.black else color.n200)){
        Text(
            text = text,
            style = MaterialTheme.typography.body1.merge(),
            color = (if (selectedItem) color.white else color.black),
            modifier = Modifier
                .clickable {
                    selectedItem = !selectedItem
                    onClick()
                }
                .padding(all = Padding().padding02),
        )
    }

}

@Composable
@Preview
fun TagPreview() {

    Column() {
        var firstItemSelected by remember { mutableStateOf(false) }
        Tag(
            selected = firstItemSelected,
            text = "Todas",
            onClick = { firstItemSelected = !firstItemSelected },
        )
        Spacer(modifier = Modifier.padding(Padding().padding01))
        var secondItemSelected by remember { mutableStateOf(true) }
        Tag(
            selected = secondItemSelected,
            text = "Todas",
            onClick = { secondItemSelected = !secondItemSelected })
    }
}