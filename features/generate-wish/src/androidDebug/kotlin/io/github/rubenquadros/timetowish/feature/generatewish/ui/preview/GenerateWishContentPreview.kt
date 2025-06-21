package io.github.rubenquadros.timetowish.feature.generatewish.ui.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import io.github.rubenquadros.timetowish.feature.generatewish.presentation.GenerateWishUiState
import io.github.rubenquadros.timetowish.feature.generatewish.presentation.ui.GenerateWishContent
import io.github.rubenquadros.timetowish.ui.preview.TWPreview
import io.github.rubenquadros.timetowish.ui.preview.TWPreviewTheme

@Composable
@TWPreview
private fun GenerateWishContentPreview(
    @PreviewParameter(GenerateWishUiStatePreviewParameterProvider::class) uiState: GenerateWishUiState
) {
    TWPreviewTheme {
        GenerateWishContent(
            generateWishUiState = uiState,
            onSubmitQuery = { _ -> },
            onNewChatClick = { },
            navigateUp = { },
            onAction = { },
            retryChatOnError = { }
        )
    }
}

private class GenerateWishUiStatePreviewParameterProvider : CollectionPreviewParameterProvider<GenerateWishUiState>(
    listOf(
        GenerateWishUiState(),

        GenerateWishUiState(conversations = getConversations())
    )
)