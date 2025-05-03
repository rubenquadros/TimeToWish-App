package io.github.rubenquadros.timetowish.feature.home.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import io.github.rubenquadros.timetowish.core.session.CurrentUser
import io.github.rubenquadros.timetowish.feature.home.domain.entity.Event
import io.github.rubenquadros.timetowish.feature.home.domain.entity.HomeEntity
import io.github.rubenquadros.timetowish.feature.home.presentation.ui.grid.HomeGrid
import io.github.rubenquadros.timetowish.feature.home.resources.Res
import io.github.rubenquadros.timetowish.feature.home.resources.home_alert
import io.github.rubenquadros.timetowish.feature.home.resources.home_event_alert_accessibility_label
import io.github.rubenquadros.timetowish.feature.home.resources.home_events
import io.github.rubenquadros.timetowish.feature.home.resources.home_events_cta
import io.github.rubenquadros.timetowish.feature.home.resources.home_hello_loggged_in
import io.github.rubenquadros.timetowish.feature.home.resources.home_hello_non_logged_in
import io.github.rubenquadros.timetowish.feature.home.resources.home_no_upcoming_events
import io.github.rubenquadros.timetowish.feature.home.resources.home_profile
import io.github.rubenquadros.timetowish.feature.home.resources.home_profile_accessibility_label
import io.github.rubenquadros.timetowish.feature.home.resources.home_tech_credit
import io.github.rubenquadros.timetowish.ui.TWTheme
import io.github.rubenquadros.timetowish.ui.button.TWButton
import io.github.rubenquadros.timetowish.ui.card.TWCard
import io.github.rubenquadros.timetowish.ui.card.TWCardDefaults
import io.github.rubenquadros.timetowish.ui.image.ImageReference
import io.github.rubenquadros.timetowish.ui.image.TWImage
import io.github.rubenquadros.timetowish.ui.text.TWText
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomeContent(
    homeEntity: HomeEntity
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TWTheme.colors.surface),
        verticalArrangement = Arrangement.spacedBy(TWTheme.spacings.space4)
    ) {
        TopBar(
            modifier = Modifier
                .padding(top = TWTheme.spacings.space2)
                .padding(start = TWTheme.spacings.space4)
                .padding(end = TWTheme.spacings.space4)
                .fillMaxWidth(),
            currentUser = homeEntity.currentUser
        )

        EventsToday(
            modifier = Modifier.padding(horizontal = TWTheme.spacings.space2).fillMaxWidth(),
            todayEvents = homeEntity.todayEvents ?: persistentListOf()
        )

        HomeGrid(
            modifier = Modifier.padding(horizontal = TWTheme.spacings.space2).fillMaxWidth()
        )

        TWText(
            modifier = Modifier.padding(start = TWTheme.spacings.space4),
            text = stringResource(Res.string.home_tech_credit),
            textColor = TWTheme.colors.onSurface
        )
    }
}

@Composable
private fun TopBar(
    currentUser: CurrentUser?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(TWTheme.spacings.space2)
    ) {
        TWText(
            modifier = Modifier.weight(1f),
            text = if (currentUser == null || !currentUser.isLoggedIn) {
                stringResource(Res.string.home_hello_non_logged_in)
            } else {
                stringResource(Res.string.home_hello_loggged_in, currentUser.name)
            },
            textStyle = TWTheme.typography.titleLarge,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold,
            textColor = TWTheme.colors.onSurface
        )

        TWButton(
            content = TWButton.Content.Icon(
                imageReference = if (currentUser == null || !currentUser.isLoggedIn) {
                    ImageReference.ResImage(Res.drawable.home_profile)
                } else {
                    ImageReference.ServerImage(
                        imageUrl = currentUser.profilePic,
                        fallback = Res.drawable.home_profile
                    )
                },
                accessibilityLabel = stringResource(Res.string.home_profile_accessibility_label)
            ),
            variant = TWButton.Variant.Elevated
        ) {
            //open profile/settings
        }
    }
}

@Composable
private fun EventsToday(
    todayEvents: ImmutableList<Event>,
    modifier: Modifier = Modifier
) {
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
                tint = if (todayEvents.isEmpty()) TWTheme.colors.disabled else null,
            )
        },
        content = { EventsContent(todayEvents) }
    )
}

@Composable
private fun EventsContent(
    todayEvents: ImmutableList<Event>
) {
    val isNoEvents = remember(todayEvents) { todayEvents.isEmpty() }
    val noOfEvents = remember(todayEvents) { todayEvents.size }

    Column(
        modifier = Modifier.padding(start = TWTheme.spacings.space4, top = TWTheme.spacings.space8, bottom = TWTheme.spacings.space4),
        verticalArrangement = Arrangement.spacedBy(TWTheme.spacings.space2)
    ) {
        TWText(
            text = if (isNoEvents) {
                stringResource(Res.string.home_no_upcoming_events)
            } else {
                pluralStringResource(Res.plurals.home_events, noOfEvents, noOfEvents)
            },
            textStyle = TWTheme.typography.headlineMedium,
            textColor = TWTheme.colors.onPrimaryContainer
        )

        if (!isNoEvents) {
            TWButton(
                content = TWButton.Content.Text(
                    text = pluralStringResource(Res.plurals.home_events_cta, noOfEvents, noOfEvents)
                ),
                onClick = {
                    //open detailed events list
                }
            )
        }
    }
}