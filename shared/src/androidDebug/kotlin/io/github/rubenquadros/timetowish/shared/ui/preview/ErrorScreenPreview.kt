package io.github.rubenquadros.timetowish.shared.ui.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import io.github.rubenquadros.timetowish.shared.presentation.ui.ErrorScreen
import io.github.rubenquadros.timetowish.ui.TWTheme
import io.github.rubenquadros.timetowish.ui.preview.TWPreview
import io.github.rubenquadros.timetowish.ui.preview.TWPreviewTheme

@TWPreview
@Composable
private fun ErrorScreenPreview(
    @PreviewParameter(ErrorScreenPreviewParameterProvider::class) cta: ErrorScreen.Cta?
) {
    TWPreviewTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(TWTheme.colors.surface)
        ) {
            ErrorScreen(
                modifier = Modifier.padding(top = TWTheme.spacings.space4),
                cta = cta
            )
        }
    }
}

private class ErrorScreenPreviewParameterProvider : CollectionPreviewParameterProvider<ErrorScreen.Cta?>(
    listOf(
        null,
        ErrorScreen.Cta(
            text = "Try again",
            onClick = {}
        )
    )
)