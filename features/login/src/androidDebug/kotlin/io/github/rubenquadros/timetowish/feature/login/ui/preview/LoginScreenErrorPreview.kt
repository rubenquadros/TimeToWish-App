package io.github.rubenquadros.timetowish.feature.login.ui.preview

import androidx.compose.runtime.Composable
import io.github.rubenquadros.timetowish.feature.login.presentation.ui.LoginScreenError
import io.github.rubenquadros.timetowish.ui.preview.TWPreview
import io.github.rubenquadros.timetowish.ui.preview.TWPreviewTheme

@TWPreview
@Composable
private fun LoginScreenErrorPreview() {
    TWPreviewTheme {
        LoginScreenError(
            onRetry = {},
            navigateUp = {}
        )
    }
}