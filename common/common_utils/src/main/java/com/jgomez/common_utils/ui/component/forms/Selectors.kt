package com.jgomez.common_utils.ui.component.forms

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jgomez.common_utils.ui.theme.ClimbyTheme
import com.jgomez.common_utils.ui.theme.text.ClimbyTextStyle

@Composable
fun CardLevel(
    cardData: CardData,
    selected: Boolean,
    onClick: () -> Unit,
    theme: ClimbyTheme = ClimbyTheme(),
) {
    val mutableWidth = if (selected) 3.dp else 0.dp
    val mutableColor = if (selected) theme.color.accent else Color.Transparent
    val mutableTextColor = if (selected) theme.color.accent else theme.color.black

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding()
            .border(
                width = mutableWidth,
                color = mutableColor,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(onClick = onClick),
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
                        text = cardData.title,
                        style = ClimbyTextStyle.Heading3(),
                        color = mutableTextColor
                    )
                    Spacer(modifier = Modifier.padding(bottom = 4.dp))
                    Text(
                        text = cardData.body,
                        style = ClimbyTextStyle.Heading6Body(),
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
                        selected = selected,
                        onClick = onClick
                    )
                }
            }
        }
    )
}

@Composable
fun CardLevelList(cardDataList: List<CardData>) {
    var selectedColorIndex by remember { mutableStateOf(0) }
    LazyColumn(
        modifier = Modifier.padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(cardDataList) { index, cardData ->
            CardLevel(
                cardData = cardData,
                selected = index == selectedColorIndex,
                onClick = { selectedColorIndex = index }
            )
        }
    }
}

/*@Composable
fun Selector(
    selectedOption: CardData = "Principiante",
    titles: List<CardData>,
    theme: ClimbyTheme = ClimbyTheme(),
    onClick: (String) -> Unit,
) {
    var mutableSelectedOption: String by remember { mutableStateOf(selectedOption) }
    Column(
        modifier = Modifier.padding(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        titles.forEach {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = if (it == mutableSelectedOption) 3.dp else 0.dp,
                        color = if (it.key == mutableSelectedOption) theme.color.accent else Color.Transparent,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .selectable(
                        selected = mutableSelectedOption == it.key,
                        onClick = {
                            mutableSelectedOption = it.key
                            onClick.invoke(it.key)
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
                                style = ClimbyTextStyle.Heading3(),
                                color = if (it.key == mutableSelectedOption) theme.color.accent else theme.color.black
                            )
                            Spacer(modifier = Modifier.padding(bottom = 4.dp))
                            Text(
                                text = it.value,
                                style = ClimbyTextStyle.Heading6Body(),
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
                                selected = (it.key == mutableSelectedOption),
                                onClick = {
                                    mutableSelectedOption = it.key
                                    onClick.invoke(it.key)
                                }
                            )
                        }
                    }
                }
            )
        }
    }
}*/

data class CardData(val title: String, val body: String)

@Composable
@Preview(showSystemUi = true)
fun SelectorPreview() {

    val titles = listOf(
        CardData(
            "Principante",
            "Estoy iniciándome en este deporte. Tengo todo por aprender",
        ),
        CardData(
            "Intermedio",
            "Escalo con frecuencia y me encuentro cómodo escalando en la naturaleza"
        ),
        CardData(
            "Avanzado",
            "Llevo años escalando de manera continuada. Conozco a fondo las técnicas de las modalidades que practico"
        )
    )
    CardLevelList(titles)
}