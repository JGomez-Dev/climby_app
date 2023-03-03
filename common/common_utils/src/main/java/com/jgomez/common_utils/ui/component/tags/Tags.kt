package com.jgomez.common_utils.ui.component.tags

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TagList(tagList: List<String>, selectedValue: MutableState<String>, onClickTag: (climbType : String) -> Unit) {
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        tagList.forEach { text ->
            Tag(text, onClick = { onClickTag.invoke(text) }, selectedValue = selectedValue)
        }
    }
}

@Composable
@Preview
fun TagListPreview() {
    val tagList = listOf(
        "Todas",
        "Próximo fin de Semana",
        "Boulder",
        "Deportiva",
        "Rocódromo",
        "Clásica",
        "Psicobloc"
    )
    var selectedButton = remember { mutableStateOf("Todas") }
    TagList(tagList, selectedValue = selectedButton) {}
}