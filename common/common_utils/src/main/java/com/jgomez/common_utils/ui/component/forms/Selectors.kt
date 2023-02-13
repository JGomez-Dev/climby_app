package com.jgomez.common_utils.ui.component.forms

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jgomez.common_utils.ui.theme.ClimbyTheme

@Composable
fun Selector(
    title: Map<String, String>,
    theme: ClimbyTheme = ClimbyTheme(),
    onClick: () -> Unit,
) {
    var selectedOption by remember {
        mutableStateOf(title.getValue("Principante"))
    }
    Column(
        modifier = Modifier.padding(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        title.forEach {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = if (it.key == selectedOption) 3.dp else 0.dp,
                        color = theme.color.accent,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .selectable(
                        selected = selectedOption == it.key,
                        onClick = {
                            onClick()
                            selectedOption = it.key
                        }
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = theme.color.white,
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = theme.elevation.elevation03.blur),
                content = {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .padding(
                                top = theme.padding.padding03,
                                bottom = theme.padding.padding03,
                                start = theme.padding.padding03,
                                end = theme.padding.padding04
                            )

                    ) {
                        Column(
                            Modifier
                                .align(CenterVertically)
                                .weight(1f)
                        ) {
                            Text(
                                text = it.key,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (it.key == selectedOption) theme.color.accent else theme.color.black
                            )
                            Spacer(modifier = Modifier.padding(bottom = 4.dp))
                            Text(
                                text = it.value,
                                fontSize = 14.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(end = 28.dp),
                                color = theme.color.n600,
                            )
                        }
                        Box(
                            modifier = Modifier
                                .align(CenterVertically)
                        ) {
                            RadioButton(
                                modifier = Modifier
                                    .align(CenterEnd)
                                    .size(24.dp),
                                selected = (it.key == selectedOption),
                                onClick = {
                                    onClick()
                                    selectedOption = it.key
                                }
                            )
                        }
                    }
                }
            )
        }
    }
}

@Composable
@Preview
fun SelectorPreview() {
    val titles = mapOf(
        "Avanzado" to "Estoy iniciándome en este deporte. Tengo todo por aprender",
        "Principante" to "Escalo con frecuencia y me encuentro cómodo escalando en la naturaleza",
        "Intermedio" to "Llevo años escalando de manera continuada. Conozco a fondo las técnicas de las modalidades que practico"
    )
    Selector(
        onClick = {},
        title = titles
    )
}