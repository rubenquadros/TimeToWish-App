package io.github.rubenquadros.timetowish.ui.banner

import androidx.compose.material3.CardColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import io.github.rubenquadros.timetowish.ui.TWTheme

@Immutable
internal data class TWBannerColors(
    val containerColor: Color,
    val contentColor: Color,
    val borderColor: Color
)

@Composable
internal fun TWBannerColors.toCardColors(): CardColors {
    return CardColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContentColor = TWTheme.colors.disabled,
        disabledContainerColor = TWTheme.colors.onDisabled
    )
}