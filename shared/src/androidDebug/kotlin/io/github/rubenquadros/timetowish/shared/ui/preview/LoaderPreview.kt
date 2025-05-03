package io.github.rubenquadros.timetowish.shared.ui.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import io.github.rubenquadros.timetowish.shared.presentation.ui.Loader
import io.github.rubenquadros.timetowish.ui.TWTheme
import io.github.rubenquadros.timetowish.ui.preview.TWPreviewTheme

@PreviewLightDark
@Composable
private fun LoaderPreview() {
    TWPreviewTheme {
        Box(modifier = Modifier.fillMaxSize().background(TWTheme.colors.surface)) {
            Loader(modifier = Modifier.fillMaxSize())
        }
    }
}