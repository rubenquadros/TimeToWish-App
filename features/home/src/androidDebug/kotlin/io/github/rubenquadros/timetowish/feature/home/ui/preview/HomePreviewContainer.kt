package io.github.rubenquadros.timetowish.feature.home.ui.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.rubenquadros.timetowish.ui.TWTheme
import io.github.rubenquadros.timetowish.ui.preview.TWPreviewTheme

@Composable
internal fun HomePreviewContainer(content: @Composable () -> Unit) {
    TWPreviewTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(TWTheme.colors.surface)
        ) {
            content()
        }
    }
}