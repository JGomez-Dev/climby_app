package com.jgomez.common_utils.ui.component.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jgomez.common_utils.R
import com.jgomez.common_utils.ui.theme.ClimbyTheme
import com.jgomez.common_utils.ui.utils.SafeClickManager
import com.jgomez.common_utils.ui.wrapper.ClimbyImage
import com.jgomez.common_utils.ui.wrapper.painter

@Composable
fun CardUserJoined(
    name: String,
    level: String,
    outputs: Int,
    photoUser: ClimbyImage,
    score: Double = 0.0,
    theme: ClimbyTheme = ClimbyTheme(),
    customDebounceTimeMS: Long = SafeClickManager.defaultButtonDebounceTimeMs
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, bottom = 16.dp, end = 12.dp),
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
                Row() {
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
                        Row() {
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
                                text = outputs.toString() + " Salidas", modifier = Modifier
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
                    Row(
                        modifier = Modifier
                            .align(CenterVertically)
                    ) {
                        Starts(score)
                    }
                }
            }
        })
}

@Composable
@Preview
fun CardUserJoinedPreview() {
    val score: Double by remember { mutableStateOf(0.5) }
    CardUserJoined(
        "Javier",
        "Intermedio",
        12,
        ClimbyImage.Resource(id = R.drawable.albarracin),
        score
    )
}

