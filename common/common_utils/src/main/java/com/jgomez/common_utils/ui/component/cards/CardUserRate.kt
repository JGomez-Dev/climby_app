package com.jgomez.common_utils.ui.component.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
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
import com.jgomez.common_utils.ui.utils.SafeClickManager.defaultButtonDebounceTimeMs
import com.jgomez.common_utils.ui.wrapper.ClimbyImage
import com.jgomez.common_utils.ui.wrapper.painter


@Composable
fun RateUserCard(
    name: String,
    photoUser: ClimbyImage,
    score: Double = 0.0,
    onClickMinus: ((Double) -> Unit)? = null,
    onClickPlus: ((Double) -> Unit)? = null,
    theme: ClimbyTheme = ClimbyTheme(),
    customDebounceTimeMS: Long = defaultButtonDebounceTimeMs
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
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
                    Text(
                        text = name, modifier = Modifier
                            .align(CenterVertically)
                            .padding(start = theme.padding.padding02),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = theme.color.black
                    )
                }

                Row(
                    modifier = Modifier
                        .align(CenterVertically)
                ) {
                    Box(
                        modifier = Modifier
                            .align(CenterVertically)
                            .padding(end = theme.padding.padding01),
                    ) {
                        onClickMinus?.invoke(score)
                        Image(
                            painter = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.minus)),
                            contentDescription = null,
                            modifier = Modifier
                                .size(32.dp),
                        )
                    }
                    Row(
                        modifier = Modifier
                            .align(CenterVertically)
                    ) {
                        Starts(score)
                    }
                    Box(
                        modifier = Modifier
                            .align(CenterVertically)
                            .padding(start = theme.padding.padding01)
                    ) {
                        onClickPlus?.invoke(score)
                        Image(
                            painter = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.plus)),
                            contentDescription = null,
                            modifier = Modifier
                                .size(32.dp)
                        )
                    }
                }
            }
        })
}

@Composable
fun Starts(score: Double) {
    if (score <= 0.5) {
        Image(
            painter = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.favoriteempty)),
            contentDescription = null,
            modifier = Modifier
                .size(16.dp),
        )
        for (i in 1..2) {
            Image(
                painter = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.favoriteempty)),
                contentDescription = null,
                modifier = Modifier
                    .size(16.dp),
            )
        }
    } else if (score > 0.5) {
        Image(
            painter = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.favoritemid)),
            contentDescription = null,
            modifier = Modifier
                .size(16.dp),
        )
        for (i in 1..2) {
            Image(
                painter = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.favoriteempty)),
                contentDescription = null,
                modifier = Modifier
                    .size(16.dp),
            )
        }
    }
}

@Composable
@Preview
fun RateUserCardPreview() {
    var score: Double by remember { mutableStateOf(0.5) }
    RateUserCard(
        "Javier",
        ClimbyImage.Resource(id = R.drawable.albarracin),
        score,
        onClickMinus = { score = it },
        onClickPlus = { score = it })
}



