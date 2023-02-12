package com.jgomez.common_utils.ui.component.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jgomez.common_utils.R
import com.jgomez.common_utils.ui.component.forms.Starts
import com.jgomez.common_utils.ui.theme.ClimbyTheme
import com.jgomez.common_utils.ui.wrapper.ClimbyImage
import com.jgomez.common_utils.ui.wrapper.painter

@Composable
fun CardUserJoined(
    name: String,
    level: String,
    outputs: Int,
    photoUser: ClimbyImage,
    score: Int = 0,
    theme: ClimbyTheme = ClimbyTheme(),
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = theme.color.white,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = theme.elevation.elevation03.blur),
        content = {
            Row(
                Modifier
                    .padding(theme.padding.padding03)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
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
                        Text(
                            text = name, modifier = Modifier
                                .align(Start)
                                .padding(start = theme.padding.padding02),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = theme.color.black
                        )
                        Row {
                            Text(
                                text = level,
                                modifier = Modifier
                                    .align(CenterVertically)
                                    .padding(start = theme.padding.padding02),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = theme.color.n600,
                            )
                            Spacer(modifier = Modifier.padding(end = 8.dp))
                            Text(
                                text = "$outputs Salidas", modifier = Modifier
                                    .align(CenterVertically)
                                    .padding(start = theme.padding.padding02),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = theme.color.n600
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .align(CenterVertically)
                ) {
                    Starts(score)
                }
            }
        })
}

@Composable
@Preview
fun CardUserJoinedPreview() {
    val score: Int by remember { mutableStateOf(1) }
    CardUserJoined(
        "Javier",
        "Intermedio",
        12,
        ClimbyImage.Resource(id = R.drawable.albarracin),
        score
    )
}

