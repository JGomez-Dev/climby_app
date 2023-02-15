package com.jgomez.common_utils.ui.component.tags

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TagList(tagList: List<String>,  onClick: () -> Unit) {
    Row(Modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        tagList.forEach { text ->
            Tag(text, onClick = onClick)
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

    TagList(tagList, onClick = {})
}