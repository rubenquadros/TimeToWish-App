package io.github.rubenquadros.timetowish.ui.preview.text

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import io.github.rubenquadros.timetowish.ui.TWTheme
import io.github.rubenquadros.timetowish.ui.preview.TWPreviewTheme
import io.github.rubenquadros.timetowish.ui.text.TWTitle

@PreviewLightDark
@Composable
private fun TWTitlePreview() {
    TWPreviewTheme {
        Box(modifier = Modifier.fillMaxWidth().background(TWTheme.colors.surface)) {
            TWTitle(
                modifier = Modifier.padding(TWTheme.spacings.space2).align(Alignment.Center),
                title = "Title 1"
            )
        }
    }
}