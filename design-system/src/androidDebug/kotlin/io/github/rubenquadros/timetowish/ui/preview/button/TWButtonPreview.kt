package io.github.rubenquadros.timetowish.ui.preview.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import io.github.rubenquadros.timetowish.ui.TWTheme
import io.github.rubenquadros.timetowish.ui.button.TWButton
import io.github.rubenquadros.timetowish.ui.image.ImageReference
import io.github.rubenquadros.timetowish.ui.preview.TWPreviewTheme
import timetowish.design_system.generated.resources.Res
import timetowish.design_system.generated.resources.compose_multiplatform_logo

@PreviewLightDark
@Composable
private fun TWTextButtonEnabledPreview() {
    TWPreviewTheme {
        Column(
            modifier = Modifier.background(TWTheme.colors.surfaceContainer).padding(TWTheme.spacings.space4),
            verticalArrangement = Arrangement.spacedBy(TWTheme.spacings.space4)
        ) {
            variantList.forEach { variant ->
                TWButton(
                    variant = variant,
                    content = TWButton.Content.Text(
                        text = "Click me!"
                    ),
                    onClick = { }
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun TWTextButtonDisabledPreview() {
    TWPreviewTheme {
        Column(
            modifier = Modifier.background(TWTheme.colors.surfaceContainer).padding(TWTheme.spacings.space4),
            verticalArrangement = Arrangement.spacedBy(TWTheme.spacings.space4)
        ) {
            variantList.forEach { variant ->
                TWButton(
                    variant = variant,
                    content = TWButton.Content.Text(
                        text = "Click me!"
                    ),
                    isEnabled = false,
                    onClick = { }
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun TWIconButtonEnabledPreview() {
    TWPreviewTheme {
        Column(
            modifier = Modifier.background(TWTheme.colors.surfaceContainer).padding(TWTheme.spacings.space4),
            verticalArrangement = Arrangement.spacedBy(TWTheme.spacings.space4)
        ) {
            variantList.forEach { variant ->
                TWButton(
                    variant = variant,
                    content = TWButton.Content.Icon(
                        imageReference = ImageReference.ResImage(Res.drawable.compose_multiplatform_logo),
                        accessibilityLabel = "Compose multiplatform logo"
                    ),
                    isEnabled = true,
                    onClick = { }
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun TWIconButtonDisabledPreview() {
    TWPreviewTheme {
        Column(
            modifier = Modifier.background(TWTheme.colors.surfaceContainer).padding(TWTheme.spacings.space4),
            verticalArrangement = Arrangement.spacedBy(TWTheme.spacings.space4)
        ) {
            variantList.forEach { variant ->
                TWButton(
                    variant = variant,
                    content = TWButton.Content.Icon(
                        imageReference = ImageReference.ResImage(Res.drawable.compose_multiplatform_logo),
                        accessibilityLabel = "Compose multiplatform logo"
                    ),
                    isEnabled = false,
                    onClick = { }
                )
            }
        }
    }
}

private val variantList: List<TWButton.Variant> @Composable get() = listOf(
    TWButton.Variant.Primary,
    TWButton.Variant.Secondary,
    TWButton.Variant.Tertiary,
    TWButton.Variant.TertiaryTinted(color = TWTheme.colors.onSurface),
    TWButton.Variant.Elevated
)