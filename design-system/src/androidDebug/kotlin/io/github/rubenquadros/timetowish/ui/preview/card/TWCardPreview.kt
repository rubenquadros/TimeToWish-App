package io.github.rubenquadros.timetowish.ui.preview.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import io.github.rubenquadros.timetowish.ui.TWTheme
import io.github.rubenquadros.timetowish.ui.card.TWCard
import io.github.rubenquadros.timetowish.ui.image.ImageReference
import io.github.rubenquadros.timetowish.ui.image.TWImage
import io.github.rubenquadros.timetowish.ui.preview.TWPreviewTheme
import io.github.rubenquadros.timetowish.ui.text.TWText
import timetowish.design_system.generated.resources.Res
import timetowish.design_system.generated.resources.compose_multiplatform_logo

@PreviewLightDark
@Composable
private fun TWCardWithVisualContentPreview(
    @PreviewParameter(TWCardVisualContentPlacementProvider::class)
    visualContentPlacement: TWCard.VisualContentPlacement
) {
    TWPreviewTheme {
        Box(modifier = Modifier
            .padding(TWTheme.spacings.space8)
            .background(color = TWTheme.colors.surface, shape = TWTheme.shapes.small)
        ) {
            TWCard(
                variant = TWCard.Variant.Default,
                visualContentPlacement = visualContentPlacement,
                visualContent = {
                    TWImage(
                        modifier = Modifier.size(TWTheme.spacings.space16),
                        imageReference = ImageReference.ResImage(Res.drawable.compose_multiplatform_logo),
                        accessibilityLabel = ""
                    )
                },
                content = {
                    CardContent()
                }
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun TWCardWithoutVisualContentPreview(
    @PreviewParameter(TWCardVariantProvider::class)
    variant: TWCard.Variant
) {
    TWPreviewTheme {
        Box(modifier = Modifier
            .padding(TWTheme.spacings.space8)
            .background(color = TWTheme.colors.surface, shape = TWTheme.shapes.small)
        ) {
            TWCard(
                variant = variant
            ) {
                CardContent()
            }
        }
    }
}

@Composable
private fun CardContent() {
    Column(
        modifier = Modifier.padding(TWTheme.spacings.space4),
        verticalArrangement = Arrangement.spacedBy(TWTheme.spacings.space1)
    ) {
        TWText(
            text = "Heading",
            textStyle = TWTheme.typography.titleMedium
        )
        TWText(
            text = "Description",
            textStyle = TWTheme.typography.bodyMedium
        )
    }
}

private class TWCardVariantProvider: CollectionPreviewParameterProvider<TWCard.Variant>(
    listOf(TWCard.Variant.Default, TWCard.Variant.Elevated)
)

private class TWCardVisualContentPlacementProvider: CollectionPreviewParameterProvider<TWCard.VisualContentPlacement>(
    listOf(TWCard.VisualContentPlacement.Top, TWCard.VisualContentPlacement.Start, TWCard.VisualContentPlacement.End)
)