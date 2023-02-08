package com.jgomez.discover_presentation.views.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jgomez.common_utils.R
import com.jgomez.common_utils.ui.component.buttons.Button
import com.jgomez.common_utils.ui.component.buttons.ButtonType
import com.jgomez.common_utils.ui.component.cards.RateUserCard
import com.jgomez.common_utils.ui.component.cards.RateUserCardPreview
import com.jgomez.common_utils.ui.component.imagery.Image
import com.jgomez.common_utils.ui.component.pills.Pills
import com.jgomez.common_utils.ui.theme.ClimbyColor
import com.jgomez.common_utils.ui.theme.Padding
import com.jgomez.common_utils.ui.wrapper.ClimbyImage

@Composable
fun TravelCard(painterTwoUsers: List<Painter>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Padding().padding03),
        colors = CardDefaults.cardColors(
            containerColor = ClimbyColor().white,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        content = {
            Column() {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier
                            .padding(top = 20.dp, start = 20.dp)
                            .weight(2f)
                    ) {
                        Text(
                            text = "Deportiva en",
                            maxLines = 1,
                            fontStyle = FontStyle.Normal,
                            fontSize = 14.sp,
                            color = ClimbyColor().black.copy(0.5f)
                        )
                        Text(
                            text = "La Pedriza, 19 de enero",
                            maxLines = 2,
                            fontStyle = FontStyle.Normal,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = ClimbyColor().black
                        )
                    }
                    Spacer(Modifier.weight(0.7f))
                    Box(modifier = Modifier.weight(1f)) {
                        Image(
                            painterResource(id = R.drawable.sputnik),
                            painterResource(id = R.drawable.mark_01)
                        )
                    }
                }
            }
            Column() {
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
                        Pills(painters = painterTwoUsers)
                    }
                    Box(modifier = Modifier.weight(1.3f)) {
                        Button(
                            title = "Pedir unirme",
                            subTitle = "2 plazas",
                            textPadding = 8.dp,
                            onClick = {},
                            type = ButtonType.EnableWithSubtitle
                        )
                    }
                }
            }
        }
    )
}

@Composable
@Preview(showBackground = true)
fun TravelCarPreview() {
    val painterTwoUsers = listOf(
        painterResource(id = R.drawable.sputnik),
        painterResource(id = R.drawable.mallorca),
        painterResource(id = R.drawable.cuenca),
        painterResource(id = R.drawable.chulilla),
        painterResource(id = R.drawable.fin_del_mundo),
    )
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        TravelCard(painterTwoUsers)
        Spacer(modifier = Modifier.padding(bottom = 16.dp))
        TravelCard(painterTwoUsers)
    }
}