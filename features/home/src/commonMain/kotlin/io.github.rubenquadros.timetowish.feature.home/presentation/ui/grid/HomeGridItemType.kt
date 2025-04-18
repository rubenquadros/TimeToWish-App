package io.github.rubenquadros.timetowish.feature.home.presentation.ui.grid

import androidx.compose.runtime.Composable
import io.github.rubenquadros.timetowish.feature.home.resources.Res
import io.github.rubenquadros.timetowish.feature.home.resources.home_add_event
import io.github.rubenquadros.timetowish.feature.home.resources.home_add_event_accessibility_label
import io.github.rubenquadros.timetowish.feature.home.resources.home_add_event_description
import io.github.rubenquadros.timetowish.feature.home.resources.home_add_event_title
import io.github.rubenquadros.timetowish.feature.home.resources.home_calendar
import io.github.rubenquadros.timetowish.feature.home.resources.home_calendar_accessibility_label
import io.github.rubenquadros.timetowish.feature.home.resources.home_calendar_description
import io.github.rubenquadros.timetowish.feature.home.resources.home_calendar_title
import io.github.rubenquadros.timetowish.feature.home.resources.home_create_wish
import io.github.rubenquadros.timetowish.feature.home.resources.home_create_wish_accessibility_label
import io.github.rubenquadros.timetowish.feature.home.resources.home_create_wish_description
import io.github.rubenquadros.timetowish.feature.home.resources.home_create_wish_title
import io.github.rubenquadros.timetowish.feature.home.resources.home_greeting_card
import io.github.rubenquadros.timetowish.feature.home.resources.home_greeting_card_accessibility_label
import io.github.rubenquadros.timetowish.feature.home.resources.home_greeting_card_description
import io.github.rubenquadros.timetowish.feature.home.resources.home_greeting_card_title
import io.github.rubenquadros.timetowish.ui.image.ImageReference
import org.jetbrains.compose.resources.stringResource

internal enum class HomeGridItemType {
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