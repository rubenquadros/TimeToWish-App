package io.github.rubenquadros.timetowish.feature.home.presentation.ui.grid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.semantics
import io.github.rubenquadros.timetowish.ui.TWTheme
import io.github.rubenquadros.timetowish.ui.card.TWCard
import io.github.rubenquadros.timetowish.ui.image.TWImage
import io.github.rubenquadros.timetowish.ui.text.TWText
import io.github.rubenquadros.timetowish.ui.text.TWTitle

@Composable
internal fun HomeGrid(modifier: Modifier = Modifier) {
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