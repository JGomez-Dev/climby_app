package com.jgomez.discover_presentation.views.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jgomez.common_utils.R
import com.jgomez.common_utils.ui.component.cards.TravelCard
import com.jgomez.common_utils.ui.component.tags.TagList
import com.jgomez.common_utils.ui.theme.ClimbyTheme
import com.jgomez.common_utils.ui.theme.text.ClimbyTextStyle
import com.jgomez.common_utils.ui.wrapper.ClimbyImage
import com.jgomez.common_utils.ui.wrapper.painter
import com.jgomez.discover_domain.model.CardInformation
import com.jgomez.discover_presentation.viewmodel.DiscoverViewModel

@Composable
fun DiscoverContent(
    photoUrl: String,
    name: String,
    phone: String,
    email: String,
    experience: String,
    theme: ClimbyTheme = ClimbyTheme(),
    viewModel: DiscoverViewModel = hiltViewModel(),
    cardOnClick: (idTravel: Int) -> Unit,
) {
    val tripState by viewModel.trips.collectAsState()
    var mutableList: List<CardInformation> by remember { mutableStateOf(listOf()) }
    val selectedButton = remember { mutableStateOf(viewModel.getTypes().first()) }

    when {
        tripState.isLoading -> {
            Column {
                CircularProgressIndicator()
            }
        }

        tripState.error.isNotBlank() -> {}
        tripState.data?.isNotEmpty() == true -> {
            mutableList = tripState.data!!
        }
    }

    Column {
        Row(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .fillMaxWidth()
                .clickable(
                    role = Role.Button,
                    onClick = { /* Acción al hacer clic */ },
                ),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Próximas salidas desde ",
                style = ClimbyTextStyle.Heading5Label(),
                color = theme.color.n600,
                textAlign = TextAlign.Center,

                )
            Text(
                text = "Madrid",
                style = ClimbyTextStyle.Heading5Label(),
                textAlign = TextAlign.Center,
            )
            Image(
                painter = ClimbyImage.Resource(id = R.drawable.arrow_down).painter,
                contentDescription = "null",
                modifier = Modifier.align(CenterVertically)
            )
        }

        TagList(
            tagList = viewModel.getTypes(),
            selectedValue = selectedButton
        ) {
            selectedButton.value = it
        }
        Spacer(modifier = Modifier.padding(bottom = 8.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 16.dp, top = 8.dp, end = 16.dp),
        ) {
            items(
                filter(mutableList, selectedButton.value, viewModel.getTypes())
            ) { cardData ->
                Cards(cardData, cardOnClick)
                Spacer(modifier = Modifier.padding(bottom = 16.dp))
            }
        }
    }
}

@Composable
fun Cards(data: CardInformation, cardOnClick: (Int) -> Unit) {
    TravelCard(
        cardTextType = data.climbingType,
        cardTextZone = data.school,
        cardTextDate = data.departureDate,
        cardImageType = painterResource(data.schoolPhoto),
        cardPainterAssistantsList = data.usersPhoto,
        cardOnClick = { cardOnClick(data.id) },
        buttonTitle = "Pedir unime",
        buttonOnClick = {},
    )
}


fun filter(
    list: List<CardInformation>,
    filter: String,
    types: List<String>
): List<CardInformation> {
    var listFiltered: List<CardInformation> = list
    if (filter != types.first())
        listFiltered = list.filter { text ->
            text.climbingType.split(" ")[0] == filter
        }
    return listFiltered
}


@Composable
@Preview
private fun DiscoverContentPreview() {
    DiscoverContent("", "", "", "", "", cardOnClick = {})
}
