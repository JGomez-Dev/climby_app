package com.jgomez.discover_presentation.views.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jgomez.common_utils.R
import com.jgomez.common_utils.ui.component.buttons.MenuButton
import com.jgomez.common_utils.ui.component.buttons.MenuButtonType
import com.jgomez.common_utils.ui.component.cards.CardUserJoined
import com.jgomez.common_utils.ui.component.forms.CardLevel
import com.jgomez.common_utils.ui.component.toolbar.TopBar
import com.jgomez.common_utils.ui.theme.ClimbyTheme
import com.jgomez.common_utils.ui.wrapper.ClimbyImage
import com.jgomez.discover_domain.model.Booking
import com.jgomez.discover_domain.model.CardInformation
import com.jgomez.discover_domain.model.User
import com.jgomez.discover_presentation.viewmodel.DiscoverViewModel
import com.jgomez.discover_presentation.R.string as RC

@Composable
fun CardDetailContent(
    name: String,
    onClick: () -> Unit,
    idTravel: Int,
    onClickBack: () -> Unit,
    theme: ClimbyTheme = ClimbyTheme(),
    viewModel: DiscoverViewModel = hiltViewModel(),
) {
    val tripDetail by viewModel.tripDetail.collectAsState()
    viewModel.getTravelDetail(idTravel)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(theme.color.n100)
            .fillMaxHeight()
    ) {
        TopBar(
            ClimbyImage.Resource(id = R.drawable.arrow_back),
            stringResource(RC.topbar_card_detail),
            onClick = onClickBack
        )

        when {
            tripDetail.isLoading -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                }
            }

            tripDetail.error.isNotBlank() -> {}
            tripDetail.data?.id != null -> {
                FillCardWithUsers(
                    tripDetail.data!!.owner,
                    tripDetail.data!!.reservations,
                ) {
                    /*TODO*/
                }
            }
        }

    }
}

@Composable
fun FillCardWithUsers(owner: User, passagers: List<Booking>, cardOnClick: (Int) -> Unit) {
    CardUserJoined(
        name = owner.fullName,
        level = owner.experience,
        outputs = owner.outputs,
        photoUser = owner.userPhoto,
        score = owner.score,

        )
}

@Composable
@Preview
fun CardDetailContentPreview() {
    CardDetailContent("Hola", onClick = {}, 1, onClickBack = {})
}