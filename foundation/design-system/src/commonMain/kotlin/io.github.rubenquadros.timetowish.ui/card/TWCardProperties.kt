package io.github.rubenquadros.timetowish.ui.card

import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import io.github.rubenquadros.timetowish.ui.TWTheme

@Immutable
data class TWCardColors(
    val containerColor: Color,
    val disabledContainerColor: Color
)

object TWCardDefaults {
    @Composable
    fun cardColors(
        containerColor: Color = TWTheme.colors.surfaceContainer,
        disabledContainerColor: Color = TWTheme.colors.disabledVariant
    ): TWCardColors = TWCardColors(
        containerColor = containerColor,
        disabledContainerColor = disabledContainerColor
    )

    @Composable
    fun cardShape(): Shape = TWTheme.shapes.small
}

@Composable
internal fun TWCardColors.toCardColors(): CardColors {
    return CardDefaults.cardColors(
            containerColor = containerColor,
            disabledContainerColor = disabledContainerColor
    )
}