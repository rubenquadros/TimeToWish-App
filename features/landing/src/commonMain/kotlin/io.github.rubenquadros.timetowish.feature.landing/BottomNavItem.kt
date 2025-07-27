package io.github.rubenquadros.timetowish.feature.landing

import androidx.compose.runtime.Composable
import io.github.rubenquadros.timetowish.feature.landing.resources.Res
import io.github.rubenquadros.timetowish.feature.landing.resources.add_event
import io.github.rubenquadros.timetowish.feature.landing.resources.add_event_selected
import io.github.rubenquadros.timetowish.feature.landing.resources.ai_wish
import io.github.rubenquadros.timetowish.feature.landing.resources.ai_wish_selected
import io.github.rubenquadros.timetowish.feature.landing.resources.calendar
import io.github.rubenquadros.timetowish.feature.landing.resources.calendar_selected
import io.github.rubenquadros.timetowish.feature.landing.resources.card
import io.github.rubenquadros.timetowish.feature.landing.resources.home
import io.github.rubenquadros.timetowish.feature.landing.resources.home_selected
import io.github.rubenquadros.timetowish.feature.landing.resources.landing_add_event
import io.github.rubenquadros.timetowish.feature.landing.resources.landing_calendar
import io.github.rubenquadros.timetowish.feature.landing.resources.landing_create_wish
import io.github.rubenquadros.timetowish.feature.landing.resources.landing_greeting_card
import io.github.rubenquadros.timetowish.feature.landing.resources.landing_home
import io.github.rubenquadros.timetowish.navigation.routes.TWDestination
import io.github.rubenquadros.timetowish.navigation.routes.generatewish.GenerateWishScreen
import io.github.rubenquadros.timetowish.navigation.routes.home.HomeScreen
import io.github.rubenquadros.timetowish.ui.image.ImageReference
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource

internal data class BottomNavItem(
    val icon: ImageReference,
    val selectedIcon: ImageReference,
    val accessibilityLabel: String,
    val destination: TWDestination
)

@Composable
internal fun bottomDestinations() = persistentListOf(
    BottomNavItem(
        icon = ImageReference.ResImage(
            res = Res.drawable.home
        ),
        selectedIcon = ImageReference.ResImage(
            res = Res.drawable.home_selected
        ),
        accessibilityLabel = stringResource(Res.string.landing_home),
        destination = HomeScreen
    ), //HOME

    BottomNavItem(
        icon = ImageReference.ResImage(
            res = Res.drawable.calendar
        ),
        selectedIcon = ImageReference.ResImage(
            res = Res.drawable.calendar_selected
        ),
        accessibilityLabel = stringResource(Res.string.landing_calendar),
        destination = GenerateWishScreen //TODO:: replace with calendar
    ), //CALENDAR

    BottomNavItem(
        icon = ImageReference.ResImage(
            res = Res.drawable.add_event
        ),
        selectedIcon = ImageReference.ResImage(
            res = Res.drawable.add_event_selected
        ),
        accessibilityLabel = stringResource(Res.string.landing_add_event),
        destination = GenerateWishScreen //TODO:: replace with add event
    ), //ADD EVENT

    BottomNavItem(
        icon = ImageReference.ResImage(
            res = Res.drawable.ai_wish
        ),
        selectedIcon = ImageReference.ResImage(
            res = Res.drawable.ai_wish_selected
        ),
        accessibilityLabel = stringResource(Res.string.landing_create_wish),
        destination = GenerateWishScreen
    ), //AI WISH

    BottomNavItem(
        icon = ImageReference.ResImage(
            res = Res.drawable.card
        ),
        selectedIcon = ImageReference.ResImage(
            res = Res.drawable.calendar_selected
        ),
        accessibilityLabel = stringResource(Res.string.landing_greeting_card),
        destination = GenerateWishScreen //TODO:: replace with greeting card
    ), //AI WISH
)
