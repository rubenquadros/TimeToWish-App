package io.github.rubenquadros.timetowish.ui.preview.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import io.github.rubenquadros.timetowish.ui.image.ImageReference
import io.github.rubenquadros.timetowish.ui.image.TWImage
import io.github.rubenquadros.timetowish.ui.preview.TWPreviewTheme
import timetowish.design_system.generated.resources.Res
import timetowish.design_system.generated.resources.compose_multiplatform_logo

@PreviewLightDark
@Composable
private fun TWImagePreview() {
    TWPreviewTheme {
        TWImage(
            imageReference = ImageReference.ResImage(Res.drawable.compose_multiplatform_logo),
            accessibilityLabel = "Compose multiplatform logo"
        )
    }
}