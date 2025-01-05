package io.github.rubenquadros.timetowish.feature.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import io.github.rubenquadros.timetowish.ui.TWTheme
import io.github.rubenquadros.timetowish.ui.button.TWButton
import io.github.rubenquadros.timetowish.ui.card.TWCard
import io.github.rubenquadros.timetowish.ui.card.TWCardDefaults
import io.github.rubenquadros.timetowish.ui.image.ImageReference
import io.github.rubenquadros.timetowish.ui.image.TWImage
import io.github.rubenquadros.timetowish.ui.text.TWText
import io.github.rubenquadros.timetowish.ui.text.TWTitle
import org.jetbrains.compose.resources.stringResource
import timetowish.features.home.generated.resources.*

@Composable
internal fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TWTheme.colors.surface)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(TWTheme.spacings.space4)
    ) {
        TopBar(
            modifier = Modifier
                .padding(top = TWTheme.spacings.space2)
                .padding(start = TWTheme.spacings.space4)
                .padding(end = TWTheme.spacings.space4)
                .fillMaxWidth()
        )

        TWText(
            modifier = Modifier.padding(start = TWTheme.spacings.space4),
            text = stringResource(Res.string.home_tech_credit),
            textColor = TWTheme.colors.onSurface
        )

        EventsToday(
            modifier = Modifier.padding(horizontal = TWTheme.spacings.space2).fillMaxWidth()
        )

        HomeGrid(
            modifier = Modifier.padding(horizontal = TWTheme.spacings.space2).fillMaxWidth()
        )
    }
}

@Composable
private fun TopBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(TWTheme.spacings.space2)
    ) {
        TWText(
            modifier = Modifier.weight(1f),
            text = stringResource(Res.string.home_hello_non_logged_in),
            textStyle = TWTheme.typography.titleLarge,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold,
            textColor = TWTheme.colors.onSurface
        )

        TWButton(
            content = TWButton.Content.Icon(
                imageReference = ImageReference.ResImage(Res.drawable.home_profile),
                accessibilityLabel = stringResource(Res.string.home_profile_accessibility_label)
            ),
            variant = TWButton.Variant.Elevated
        ) {
            //open profile/settings
        }
    }
}

@Composable
private fun EventsToday(modifier: Modifier = Modifier) {
    TWCard(
        modifier = modifier,
        variant = TWCard.Variant.Default,
        cardColors = TWCardDefaults.cardColors(
            containerColor = TWTheme.colors.primaryContainer
        ),
        visualContentPlacement = TWCard.VisualContentPlacement.End,
        visualContent = {
            TWImage(
                modifier = Modifier.padding(TWTheme.spacings.space4).size(TWTheme.spacings.space16).clearAndSetSemantics {  }, //do not read out in talkback
                imageReference = ImageReference.ResImage(Res.drawable.home_alert),
                accessibilityLabel = stringResource(Res.string.home_event_alert_accessibility_label),
                tint = TWTheme.colors.disabled,
            )
        },
        content = {
            TWText(
                modifier = Modifier.padding(start = TWTheme.spacings.space4, top = TWTheme.spacings.space8, bottom = TWTheme.spacings.space4),
                text = stringResource(Res.string.home_no_upcoming_events),
                textStyle = TWTheme.typography.headlineMedium,
                textColor = TWTheme.colors.onPrimaryContainer
            )
        }
    )
}

@Composable
private fun HomeGrid(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(TWTheme.spacings.space4),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Max),
            horizontalArrangement = Arrangement.spacedBy(TWTheme.spacings.space2)
        ) {
            HomeGridItem(
                modifier = Modifier.fillMaxSize().weight(1f),
                itemType = HomeGridItemType.CALENDAR
            ){
                //do nothing
            }
            HomeGridItem(
                modifier = Modifier.fillMaxSize().weight(1f),
                itemType = HomeGridItemType.CREATE_WISH
            ){
                //do nothing
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Max),
            horizontalArrangement = Arrangement.spacedBy(TWTheme.spacings.space2)
        ) {
            HomeGridItem(
                modifier = Modifier.fillMaxSize().weight(1f),
                itemType = HomeGridItemType.GREETING_CARD
            ) {
                //do nothing
            }
            HomeGridItem(
                modifier = Modifier.fillMaxSize().weight(1f),
                itemType = HomeGridItemType.ADD_EVENT
            ) {
                //do nothing
            }
        }
    }
}

@Composable
private fun HomeGridItem(
    itemType: HomeGridItemType,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val gridItemUi = itemType.getGridItemUi()
    TWCard(
        modifier = modifier,
        variant = TWCard.Variant.Elevated,
        visualContent = {
            TWImage(
                modifier = Modifier.padding(top = TWTheme.spacings.space3).size(TWTheme.spacings.space16).clearAndSetSemantics {  }, //do not read out in talkback
                imageReference = gridItemUi.icon,
                accessibilityLabel = gridItemUi.accessibilityLabel
            )
        },
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(TWTheme.spacings.space4).semantics(mergeDescendants = true) { },
            verticalArrangement = Arrangement.spacedBy(TWTheme.spacings.space1)
        ) {
            TWTitle(
                modifier = Modifier.fillMaxWidth(),
                title = gridItemUi.title,
                textColor = TWTheme.colors.onSurface
            )
            TWText(
                modifier = Modifier.fillMaxWidth(),
                text = gridItemUi.description,
                textColor = TWTheme.colors.surfaceTint,
                textStyle = TWTheme.typography.bodyMedium
            )
        }
    }
}

private enum class HomeGridItemType {
    CALENDAR, CREATE_WISH, GREETING_CARD, ADD_EVENT;

    @Composable
    fun getGridItemUi(): GridItemUi {
        return GridItemUi(
                icon = getIcon(),
                accessibilityLabel = getA11yHint(),
                title = getTitle(),
                description = getDescription()
            )
    }

    private fun HomeGridItemType.getIcon(): ImageReference.ResImage {
        return when (this) {
            CALENDAR -> ImageReference.ResImage(Res.drawable.home_calendar)
            CREATE_WISH -> ImageReference.ResImage(Res.drawable.home_create_wish)
            GREETING_CARD -> ImageReference.ResImage(Res.drawable.home_greeting_card)
            ADD_EVENT -> ImageReference.ResImage(Res.drawable.home_add_event)
        }
    }

    @Composable
    private fun HomeGridItemType.getA11yHint(): String {
        return when (this) {
            CALENDAR -> stringResource(Res.string.home_calendar_accessibility_label)
            CREATE_WISH -> stringResource(Res.string.home_create_wish_accessibility_label)
            GREETING_CARD -> stringResource(Res.string.home_greeting_card_accessibility_label)
            ADD_EVENT -> stringResource(Res.string.home_add_event_accessibility_label)
        }
    }

    @Composable
    private fun HomeGridItemType.getTitle(): String {
        return when (this) {
            CALENDAR -> stringResource(Res.string.home_calendar_title)
            CREATE_WISH -> stringResource(Res.string.home_create_wish_title)
            GREETING_CARD -> stringResource(Res.string.home_greeting_card_title)
            ADD_EVENT -> stringResource(Res.string.home_add_event_title)
        }
    }

    @Composable
    private fun HomeGridItemType.getDescription(): String {
        return when (this) {
            CALENDAR -> stringResource(Res.string.home_calendar_description)
            CREATE_WISH -> stringResource(Res.string.home_create_wish_description)
            GREETING_CARD -> stringResource(Res.string.home_greeting_card_description)
            ADD_EVENT -> stringResource(Res.string.home_add_event_description)
        }
    }
}

private data class GridItemUi(
    val icon: ImageReference.ResImage,
    val accessibilityLabel: String,
    val title: String,
    val description: String
)