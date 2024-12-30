package io.github.rubenquadros.timetowish.ui.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.rubenquadros.timetowish.ui.TWTheme

interface TWCard {
    sealed interface Variant {
        data object Default : Variant
        data object Elevated : Variant
    }

    sealed interface VisualContentPlacement {
        data object Top : VisualContentPlacement
        data object Start : VisualContentPlacement
        data object End : VisualContentPlacement
    }
}

@Composable
fun TWCard(
    variant: TWCard.Variant,
    modifier: Modifier = Modifier,
    visualContent: (@Composable () -> Unit)? = null,
    visualContentPlacement: TWCard.VisualContentPlacement = TWCard.VisualContentPlacement.Top,
    visualContentHorizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    visualContentVerticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Card(
        modifier = modifier,
        onClick = { onClick?.invoke() },
        shape = TWTheme.shapes.small,
        colors = getCardColors(),
        elevation = getCardElevation(variant),
        border = getCardBorder()
    ) {
        when (visualContentPlacement) {
            TWCard.VisualContentPlacement.Top -> {
                Column(
                    verticalArrangement = Arrangement.spacedBy(TWTheme.spacings.space1),
                    horizontalAlignment = visualContentHorizontalAlignment
                ) {
                    visualContent?.invoke()

                    content()
                }
            }

            TWCard.VisualContentPlacement.Start -> {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(TWTheme.spacings.space1),
                    verticalAlignment = visualContentVerticalAlignment,
                ) {
                    visualContent?.invoke()

                    content()
                }
            }

            TWCard.VisualContentPlacement.End -> {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(TWTheme.spacings.space1),
                    verticalAlignment = visualContentVerticalAlignment,
                ) {
                    content()

                    visualContent?.invoke()
                }
            }
        }
    }
}

@Composable
private fun getCardElevation(variant: TWCard.Variant): CardElevation {
    return when (variant) {
        TWCard.Variant.Elevated -> {
            CardDefaults.cardElevation(
                defaultElevation = TWTheme.elevations.level2,
                pressedElevation = TWTheme.elevations.level2,
                focusedElevation = TWTheme.elevations.level2,
                hoveredElevation = TWTheme.elevations.level2,
                disabledElevation = TWTheme.elevations.level2,
                draggedElevation = TWTheme.elevations.level2
            )
        }
        else -> CardDefaults.cardElevation()
    }
}

@Composable
private fun getCardColors(): CardColors {
    return CardDefaults.cardColors(
        containerColor = TWTheme.colors.surfaceContainer,
        disabledContainerColor = TWTheme.colors.disabledVariant
    )
}

@Composable
private fun getCardBorder(): BorderStroke {
    return BorderStroke(
        width = TWTheme.borders.width1,
        color = TWTheme.colors.onSurfaceVariant
    )
}