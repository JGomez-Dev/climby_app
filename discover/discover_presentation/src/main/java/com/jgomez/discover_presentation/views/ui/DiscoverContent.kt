package com.jgomez.discover_presentation.views.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.jgomez.common_utils.R
import com.jgomez.common_utils.ui.component.cards.CardUserJoinedPreview
import com.jgomez.common_utils.ui.component.cards.RateUserCard
import com.jgomez.common_utils.ui.component.cards.RateUserCardPreview
import com.jgomez.common_utils.ui.component.tags.TagList
import com.jgomez.common_utils.ui.component.tags.TagListPreview
import com.jgomez.common_utils.ui.wrapper.ClimbyImage
import com.jgomez.discover_presentation.views.ui.component.TravelCarPreview
import com.jgomez.discover_presentation.views.ui.component.TravelCard

@Composable
fun DiscoverContent(name: String, onClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        TagListPreview()
        TravelCarPreview()
        RateUserCardPreview()
        CardUserJoinedPreview()
    }
}

@Composable
@Preview
fun DiscoverContentPreview() {
    DiscoverContent("", onClick = {})
}
