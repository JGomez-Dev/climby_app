package com.jgomez.common_utils.ui.component.tags

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jgomez.common_utils.ui.theme.Padding

@Composable
fun TagList(tagList: List<String>,  onClick: () -> Unit) {
    Row(Modifier.horizontalScroll(rememberScrollState()).padding(horizontal = 16.dp)) {
        tagList.forEach { text ->
            Row(
                modifier = Modifier
                    .padding(
                        all = Padding().padding01,
                    ),
            ) {
                Tag(text, onClick = onClick)
            }
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
        "Psicoblok"
    )

    TagList(tagList, onClick = {})
}