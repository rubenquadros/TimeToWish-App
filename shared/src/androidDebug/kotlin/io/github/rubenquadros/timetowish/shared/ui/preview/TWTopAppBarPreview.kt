package io.github.rubenquadros.timetowish.shared.ui.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import io.github.rubenquadros.timetowish.shared.presentation.ui.TWTopAppBar
import io.github.rubenquadros.timetowish.ui.image.ImageReference
import io.github.rubenquadros.timetowish.ui.preview.TWPreviewTheme
import timetowish.shared.generated.resources.Res
import timetowish.shared.generated.resources.common_back

@PreviewLightDark
@Composable
private fun TWTopAppBarTitlePreview(
    @PreviewParameter(AppBarTitlePositionPreviewParameterProvider::class) position: TWTopAppBar.TitlePosition
) {
    TWPreviewTheme {
        TWTopAppBar(
            title = TWTopAppBar.Title(
                text = "Login",
                position = position
            )
        )
    }
}

@PreviewLightDark
@Composable
private fun TWTopAppBarTitleWithIconPreview(
    @PreviewParameter(AppBarTitlePositionPreviewParameterProvider::class) position: TWTopAppBar.TitlePosition
) {
    TWPreviewTheme {
        TWTopAppBar(
            title = TWTopAppBar.Title(
                text = "Login",
                position = position
            ),
            icon = TWTopAppBar.Icon(
                imageReference = ImageReference.ResImage(Res.drawable.common_back),
                accessibilityLabel = "Back button",
                onClick = {}
            )
        )
    }
}

private class AppBarTitlePositionPreviewParameterProvider : CollectionPreviewParameterProvider<TWTopAppBar.TitlePosition>(
    listOf(TWTopAppBar.TitlePosition.START, TWTopAppBar.TitlePosition.CENTER)
)