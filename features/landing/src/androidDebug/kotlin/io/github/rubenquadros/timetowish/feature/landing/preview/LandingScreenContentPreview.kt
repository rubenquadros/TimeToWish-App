package io.github.rubenquadros.timetowish.feature.landing.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.navigation.compose.rememberNavController
import io.github.rubenquadros.timetowish.feature.landing.LandingScreenContent
import io.github.rubenquadros.timetowish.ui.preview.TWPreviewTheme

@PreviewLightDark
@Composable
private fun LandingScreenContentPreview() {
    TWPreviewTheme {
        LandingScreenContent(
            navController = rememberNavController(),
            navigateTo = { _, _ -> },
            onClick = { },
        )
    }
}