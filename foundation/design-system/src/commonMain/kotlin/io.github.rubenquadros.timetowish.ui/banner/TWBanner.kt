package io.github.rubenquadros.timetowish.ui.banner

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import io.github.rubenquadros.timetowish.ui.TWTheme
import io.github.rubenquadros.timetowish.ui.card.TWCardDefaults
import io.github.rubenquadros.timetowish.ui.image.ImageReference
import io.github.rubenquadros.timetowish.ui.image.TWImage
import io.github.rubenquadros.timetowish.ui.text.TWText

interface TWBanner {
    sealed interface Variant {
        data object Error : Variant
        data object Neutral : Variant
    }

    @Immutable
    data class TextContent(
        val text: String,
        val textStyle: TextStyle
    )

    @Immutable
    data class Icon(
        val imageReference: ImageReference,
        val accessibilityLabel: String,
        val size: IconSize = IconSize.SMALL,
        val tint: Color? = null
    )

    enum class IconSize {
        SMALL, MEDIUM, LARGE
    }
}

/**
 * @see [io.github.rubenquadros.timetowish.ui.preview.banner.TWBannerPreview]
 */
@Composable
fun TWBanner(
    variant: TWBanner.Variant,
    content: TWBanner.TextContent,
    modifier: Modifier = Modifier,
    icon: TWBanner.Icon? = null
) {
    val bannerColors = getBannerColors(variant)

    Card(
        modifier = modifier,
        colors = bannerColors.toCardColors(),
        shape = TWCardDefaults.cardShape(),
        border = BorderStroke(
            width = TWTheme.borders.width1,
            color = bannerColors.borderColor
        )
    ) {
        Row(
            modifier = Modifier.padding(TWTheme.spacings.space2).semantics(mergeDescendants = true, {}),
            horizontalArrangement = Arrangement.spacedBy(TWTheme.spacings.space2)
        ) {
            if (icon != null) {
                with(icon) {
                    TWImage(
                        modifier = Modifier.size(getSize(size)).clearAndSetSemantics {  }, //do not read in talkback
                        imageReference = imageReference,
                        accessibilityLabel = accessibilityLabel,
                        tint = tint
                    )
                }
            }

            with(content) {
                TWText(
                    text = text,
                    textStyle = textStyle
                )
            }
        }
    }
}

@Composable
private fun getBannerColors(variant: TWBanner.Variant): TWBannerColors {
    return when (variant) {
        TWBanner.Variant.Error -> {
            TWBannerColors(
                containerColor = TWTheme.colors.errorContainer,
                borderColor = TWTheme.colors.error,
                contentColor = TWTheme.colors.onError
            )
        }

        TWBanner.Variant.Neutral -> {
            TWBannerColors(
                containerColor = TWTheme.colors.disabledVariant,
                borderColor = TWTheme.colors.disabled,
                contentColor = TWTheme.colors.onDisabled
            )
        }
    }
}

@Composable
private fun getSize(iconSize: TWBanner.IconSize): Dp {
    return when (iconSize) {
        TWBanner.IconSize.SMALL -> TWTheme.spacings.space6
        TWBanner.IconSize.MEDIUM -> TWTheme.spacings.space8
        TWBanner.IconSize.LARGE -> TWTheme.spacings.space12
    }
}