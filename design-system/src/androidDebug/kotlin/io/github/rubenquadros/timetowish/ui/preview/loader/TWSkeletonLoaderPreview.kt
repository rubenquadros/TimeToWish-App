package io.github.rubenquadros.timetowish.ui.preview.loader

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import io.github.rubenquadros.timetowish.ui.TWTheme
import io.github.rubenquadros.timetowish.ui.loader.TWSkeletonLoader
import io.github.rubenquadros.timetowish.ui.preview.TWPreviewTheme

@PreviewLightDark
@Composable
private fun TWSkeletonLoaderPreview() {
    TWPreviewTheme {
        Column(
            modifier = Modifier.background(TWTheme.colors.surfaceContainer).padding(vertical = TWTheme.spacings.space4),
            verticalArrangement = Arrangement.spacedBy(TWTheme.spacings.space4)
        ) {
            variantList.forEach { variant ->
                TWSkeletonLoader(
                    modifier = Modifier.padding(horizontal = TWTheme.spacings.space2),
                    variant = variant
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun TWSkeletonLoaderColorPreview() {
    TWPreviewTheme {
        Column(
            modifier = Modifier.background(TWTheme.colors.surfaceContainer).padding(vertical = TWTheme.spacings.space4),
            verticalArrangement = Arrangement.spacedBy(TWTheme.spacings.space4)
        ) {
            variantList.forEach { variant ->
                TWSkeletonLoader(
                    modifier = Modifier.padding(horizontal = TWTheme.spacings.space2),
                    variant = variant,
                    color = TWTheme.colors.primaryContainer
                )
            }
        }
    }
}

private val variantList: List<TWSkeletonLoader.Variant> = listOf(
    TWSkeletonLoader.Variant.Title,
    TWSkeletonLoader.Variant.SingleLine,
    TWSkeletonLoader.Variant.TwoLines,
    TWSkeletonLoader.Variant.ThreeLines,
    TWSkeletonLoader.Variant.Circle(60.dp),
    TWSkeletonLoader.Variant.Box(width = 60.dp, height = 100.dp)
)