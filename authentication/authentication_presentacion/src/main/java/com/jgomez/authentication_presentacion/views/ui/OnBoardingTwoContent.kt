package com.jgomez.authentication_presentacion.views.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jgomez.common_utils.R
import com.jgomez.common_utils.ui.component.buttons.MenuButton
import com.jgomez.common_utils.ui.component.buttons.MenuButtonType
import com.jgomez.common_utils.ui.component.forms.CardData
import com.jgomez.common_utils.ui.component.forms.CardLevel
import com.jgomez.common_utils.ui.component.toolbar.TopBar
import com.jgomez.common_utils.ui.theme.ClimbyTheme
import com.jgomez.common_utils.ui.wrapper.ClimbyImage
import com.jgomez.authentication_presentacion.R.string as RC

@Composable
fun OnBoardingTwoContent(
    cardDataList: List<CardData> = getCardDataList(),
    theme: ClimbyTheme = ClimbyTheme(),
    onClickButton: (experience: Int) -> Unit,
    onClickBack: () -> Unit
) {
    var mutableSelectedCardLevel by remember { mutableStateOf(-1) }
    var mutableState: MenuButtonType by remember { mutableStateOf(MenuButtonType.Inactive) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(theme.color.n100)
            .fillMaxHeight()
    ) {
        TopBar(
            ClimbyImage.Resource(id = R.drawable.arrow_back),
            stringResource(RC.topbar_onboarding),
            onClick = onClickBack
        )
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp),
            contentPadding = PaddingValues(bottom = 16.dp, top = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            itemsIndexed(cardDataList) { index, cardData ->
                CardLevel(
                    cardData = cardData,
                    selected = index == mutableSelectedCardLevel,
                    onClick = { mutableSelectedCardLevel = index }
                )
            }

        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(all = 16.dp),
            contentAlignment = BottomCenter
        ) {
            MenuButton(
                state = mutableState,
                text = stringResource(RC.text_button_continue),
                onClick = { onClickButton(mutableSelectedCardLevel) }) {
                if (mutableSelectedCardLevel != -1) {
                    mutableState = MenuButtonType.Active
                    MenuButtonType.Active
                }else {
                    mutableState = MenuButtonType.Inactive
                    MenuButtonType.Inactive
                }
            }
        }
    }
}

@Composable
fun getCardDataList(): List<CardData> = listOf(
    CardData(
        stringResource(RC.onboarding_level_title_beginner),
        stringResource(RC.onboarding_level_body_beginner)
    ),
    CardData(
        stringResource(RC.onboarding_level_title_medium),
        stringResource(RC.onboarding_level_body_medium)
    ),
    CardData(
        stringResource(RC.onboarding_level_title_avanzaced),
        stringResource(RC.onboarding_level_body_avanzaced)
    )
)


@Composable
@Preview
fun OnBoardingTwoContentPreview() {
    OnBoardingTwoContent(onClickBack = {}, onClickButton = {})
}
