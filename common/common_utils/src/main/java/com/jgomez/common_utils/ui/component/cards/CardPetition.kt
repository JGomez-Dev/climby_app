package com.jgomez.common_utils.ui.component.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jgomez.common_utils.R
import com.jgomez.common_utils.ui.component.buttons.Button
import com.jgomez.common_utils.ui.component.buttons.ButtonState
import com.jgomez.common_utils.ui.component.forms.Starts
import com.jgomez.common_utils.ui.theme.ClimbyTheme
import com.jgomez.common_utils.ui.wrapper.ClimbyImage
import com.jgomez.common_utils.ui.wrapper.painter

@Composable
fun CardPetition(
    name: String,
    level: String,
    outputs: Int,
    photoUser: ClimbyImage,
    score: Int = 0,
    theme: ClimbyTheme = ClimbyTheme()

) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = theme.color.white,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = theme.elevation.elevation03.blur),
        content = {
            Column(
                Modifier
                    .padding(theme.padding.padding03)
                    .fillMaxWidth()
            ) {
                Row {
                    Row {
                        Image(
                            painter = photoUser.painter,
                            contentDescription = null,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Column(
                            modifier = Modifier
                                .align(CenterVertically)
                        ) {
                            Row {
                                Text(
                                    text = name, modifier = Modifier
                                        .align(CenterVertically)
                                        .padding(start = theme.padding.padding02),
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = theme.color.black
                                )
                                Box(
                                    modifier = Modifier
                                        .align(CenterVertically)
                                        .padding(start = theme.padding.padding01)
                                ) {
                                    Starts(score)
                                }
                            }
                            Row {
                                Text(
                                    text = level,
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                        .padding(start = theme.padding.padding02),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = theme.color.n600,
                                )
                                Spacer(modifier = Modifier.padding(end = 8.dp))
                                Text(
                                    text = "$outputs Salidas", modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                        .padding(start = theme.padding.padding02),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = theme.color.n600
                                )
                            }
                        }
                    }
                }
                Row(modifier = Modifier.padding(top = theme.padding.padding04)) {
                    Box(modifier = Modifier.weight(1f)){
                        Button( title = "Rechazar", onClick = {}, state = ButtonState.Inactive)
                    }
                    Spacer(modifier = Modifier.padding(start = 16.dp))
                    Box(modifier = Modifier.weight(1f)) {
                        Button(title = "Aceptar", onClick = {})
                    }
                }
            }
        }
    )
}

@Composable
@Preview
fun CardPetitionPreview() {
    CardPetition(
        name = "Javier",
        level = "Experimentado",
        outputs = 13,
        photoUser = ClimbyImage.Resource(id = R.drawable.albarracin)
    )
}