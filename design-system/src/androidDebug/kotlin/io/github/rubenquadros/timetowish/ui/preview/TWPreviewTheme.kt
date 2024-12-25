package io.github.rubenquadros.timetowish.ui.preview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalInspectionMode
import io.github.rubenquadros.timetowish.ui.TWTheme

@Composable
fun TWPreviewTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalInspectionMode provides true) {
        TWTheme { content() }
    }
}