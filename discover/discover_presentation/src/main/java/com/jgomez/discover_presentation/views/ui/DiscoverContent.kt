package com.jgomez.discover_presentation.views.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jgomez.common_utils.ui.component.cards.TravelCard
import com.jgomez.common_utils.ui.component.tags.TagListPreview
import com.jgomez.discover_domain.model.CardInformation
import com.jgomez.discover_presentation.viewmodel.DiscoverViewModel

@Composable
fun DiscoverContent(viewModel: DiscoverViewModel = hiltViewModel()) {
    val tripState by viewModel.trips.collectAsState()
    Column {
        TagListPreview()
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
                .padding(all = 16.dp)
        ) {
            when {
                tripState.isLoading -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                    }
                }

                tripState.error.isNotBlank() -> {

                }

                tripState.data?.isNotEmpty() == true -> {
                    FillCardWithTrips(tripState.data!!)
                }
            }
        }
    }
}

@Composable
fun FillCardWithTrips(data: List<CardInformation>) {
    data.forEach {
        TravelCard(
            cardTextType = it.climbing_type,
            cardTextZone = it.school_and_departure_date,
            cardOnClick = { /*TODO*/ },
            buttonTitle = "Pedir unime"
        ){ /*TOD*/ }
        Spacer(modifier = Modifier.padding(bottom = 16.dp))
    }
}

@Composable
@Preview
fun DiscoverContentPreview() {
    //DiscoverContent("") {}
}
