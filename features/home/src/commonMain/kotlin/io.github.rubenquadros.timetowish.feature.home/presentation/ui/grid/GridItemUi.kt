package io.github.rubenquadros.timetowish.feature.home.presentation.ui.grid

import io.github.rubenquadros.timetowish.ui.image.ImageReference

internal data class GridItemUi(
    val icon: ImageReference.ResImage,
    val accessibilityLabel: String,
    val title: String,
    val description: String
)