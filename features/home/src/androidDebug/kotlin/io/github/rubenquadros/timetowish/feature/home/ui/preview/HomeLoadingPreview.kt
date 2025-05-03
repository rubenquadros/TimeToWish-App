package io.github.rubenquadros.timetowish.feature.home.ui.preview

import androidx.compose.runtime.Composable
import io.github.rubenquadros.timetowish.feature.home.presentation.ui.HomeScreenLoading
import io.github.rubenquadros.timetowish.ui.preview.TWPreview

@TWPreview
@Composable
private fun HomeLoadingPreview() {
    HomePreviewContainer {
        HomeScreenLoading()
    }
}
