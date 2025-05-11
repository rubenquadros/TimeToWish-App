package io.github.rubenquadros.timetowish.ui.preview.banner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import io.github.rubenquadros.timetowish.ui.TWTheme
import io.github.rubenquadros.timetowish.ui.banner.TWBanner
import io.github.rubenquadros.timetowish.ui.image.ImageReference
import io.github.rubenquadros.timetowish.ui.preview.TWPreviewTheme
import io.github.rubenquadros.timetowish.ui.resources.Res
import io.github.rubenquadros.timetowish.ui.resources.compose_multiplatform_logo

@PreviewLightDark
@Composable
private fun TWBannerPreview(
    @PreviewParameter(BannerIconPreviewParameterProvider::class) icon: TWBanner.Icon?
) {
    TWPreviewTheme {
        Column(
            modifier = Modifier.background(TWTheme.colors.surfaceContainer).padding(TWTheme.spacings.space4),
            verticalArrangement = Arrangement.spacedBy(TWTheme.spacings.space4)
        ) {
            variantList.forEach { variant ->
                TWBanner(
                    variant = variant,
                    icon = icon,
                    content = TWBanner.TextContent(
                        text = "Sorry, there was an error when loggin you in. Please try again!",
                        textStyle = TWTheme.typography.bodyMedium
                    )
                )
            }
        }
    }
}

private val variantList = listOf(
    TWBanner.Variant.Error,
    TWBanner.Variant.Neutral
)

private class BannerIconPreviewParameterProvider : CollectionPreviewParameterProvider<TWBanner.Icon?>(
    listOf(
        null,
        TWBanner.Icon(
            imageReference = ImageReference.ResImage(Res.drawable.compose_multiplatform_logo),
            accessibilityLabel = "Compose multiplatform log",
            size = TWBanner.IconSize.SMALL
        ),
        TWBanner.Icon(
            imageReference = ImageReference.ResImage(Res.drawable.compose_multiplatform_logo),
            accessibilityLabel = "Compose multiplatform log",
            size = TWBanner.IconSize.MEDIUM
        ),
        TWBanner.Icon(
            imageReference = ImageReference.ResImage(Res.drawable.compose_multiplatform_logo),
            accessibilityLabel = "Compose multiplatform log",
            size = TWBanner.IconSize.LARGE
        ),
    )
)