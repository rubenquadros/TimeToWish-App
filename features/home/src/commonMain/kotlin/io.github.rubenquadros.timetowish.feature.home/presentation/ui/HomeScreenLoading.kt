package io.github.rubenquadros.timetowish.feature.home.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.rubenquadros.timetowish.ui.TWTheme
import io.github.rubenquadros.timetowish.ui.loader.TWSkeletonLoader

@Composable
internal fun HomeScreenLoading() {
    Column(
        modifier = Modifier
            .padding(TWTheme.spacings.space4)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(TWTheme.spacings.space4)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            TWSkeletonLoader(
                modifier = Modifier.weight(1f),
                variant = TWSkeletonLoader.Variant.TwoLines
            )

            TWSkeletonLoader(
                modifier = Modifier.padding(start = TWTheme.spacings.space2),
                variant = TWSkeletonLoader.Variant.Circle(),
            )
        }

        TWSkeletonLoader(
            modifier = Modifier.fillMaxWidth().height(EVENTS_TODAY_HEIGHT),
            variant = TWSkeletonLoader.Variant.Box()
        )

        //Grid
        Column(verticalArrangement = Arrangement.spacedBy(TWTheme.spacings.space2)) {
            GridLoading()
            GridLoading()
        }
    }
}

@Composable
private fun GridLoading() {
    Row(
        modifier = Modifier.fillMaxWidth().height(GRID_HEIGHT),
        horizontalArrangement = Arrangement.spacedBy(TWTheme.spacings.space2)
    ) {
        TWSkeletonLoader(
            modifier = Modifier.weight(1f),
            variant = TWSkeletonLoader.Variant.Box(height = GRID_HEIGHT)
        )

        TWSkeletonLoader(
            modifier = Modifier.weight(1f),
            variant = TWSkeletonLoader.Variant.Box(height = GRID_HEIGHT)
        )
    }
}

private val GRID_HEIGHT = 160.dp
private val EVENTS_TODAY_HEIGHT = 100.dp