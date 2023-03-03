package com.jgomez.common_utils.ui.component.cards

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jgomez.common_utils.R
import com.jgomez.common_utils.ui.component.buttons.Button
import com.jgomez.common_utils.ui.component.imagery.Image
import com.jgomez.common_utils.ui.component.pills.Pills
import com.jgomez.common_utils.ui.theme.ClimbyColor
import com.jgomez.common_utils.ui.theme.ClimbyTheme
import com.jgomez.common_utils.ui.theme.Padding


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TravelCard(
    cardTextType: String,
    cardTextZone: String,
    cardTextDate: String,
    cardPainterAssistantsList: List<String> = listOf(),
    cardImageType: Painter = painterResource(id = R.drawable.sputnik),
    cardImageMark: Painter = painterResource(id = R.drawable.mark_01),
    cardOnClick: () -> Unit,
    buttonTitle: String,
    buttonSubTitle: String? = null,
    buttonTextPadding: Dp = 16.dp,
    buttonEnable: Boolean = true,
    buttonNotificationsNumber: Int? = null,
    buttonColor: ClimbyColor = ClimbyColor(),
    buttonOnClick: () -> Unit,
    theme: ClimbyTheme = ClimbyTheme()
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = ClimbyColor().white,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        onClick = { cardOnClick() }
    ) {
        Column {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp)) {
                Column(
                    modifier = Modifier
                        .padding(top = 20.dp, start = 8.dp, end = 8.dp).weight(2.5f).fillMaxWidth()
                ) {
                    Text(
                        text = cardTextType,
                        style = theme.textStyle.Heading6Body(),
                        maxLines = 1,
                        color = theme.color.black.copy(0.5f)
                    )
                    Text(
                        modifier = Modifier
                            .width(IntrinsicSize.Max),
                        text = cardTextZone,
                        maxLines = 2,
                        fontStyle = FontStyle.Normal,
                        style = theme.textStyle.Heading3(),
                        color = theme.color.black
                    )
                    Text(
                        modifier = Modifier
                            .width(IntrinsicSize.Max),
                        text = cardTextDate,
                        maxLines = 2,
                        fontStyle = FontStyle.Normal,
                        style = theme.textStyle.Heading3(),
                        color = theme.color.black
                    )
                }
                Box(modifier = Modifier.weight(1f), contentAlignment = TopEnd) {
                    Image(
                        cardImageType
                    )
                }
            }
        }
        Column {
            Row(
                modifier = Modifier
                    .padding(all = Padding().padding03)
                    .align(CenterHorizontally)
            ) {
                Box(
                    modifier = Modifier
                        .align(CenterVertically)
                        .weight(2f)
                ) {
                    Pills(photoUsers = cardPainterAssistantsList)
                }
                Box(modifier = Modifier.weight(1.3f)) {
                    Button(
                        title = buttonTitle,
                        subTitle = buttonSubTitle,
                        onClick = buttonOnClick,
                        notificationsNumber = buttonNotificationsNumber,
                        color = buttonColor
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TravelCarPreview() {
    val painterTwoUsers = listOf(
        "painterResource(id = R.drawable.sputnik)",
       " painterResource(id = R.drawable.sputnik)"
    )
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        TravelCard(
            "Clasica en",
            "Sharma Climbing Madrid, ",
            cardTextDate = "27 de Septiembre",
            painterTwoUsers,
            painterResource(id = R.drawable.chulilla),
            painterResource(id = R.drawable.mark_01),
            buttonColor = ClimbyColor(),
            buttonEnable = true,
            buttonNotificationsNumber = 1,
            buttonOnClick = {},
            buttonSubTitle = "2 plazas",
            buttonTitle = "Pedir unirme",
            buttonTextPadding = 8.dp,
            cardOnClick = {}
        )
    }
}