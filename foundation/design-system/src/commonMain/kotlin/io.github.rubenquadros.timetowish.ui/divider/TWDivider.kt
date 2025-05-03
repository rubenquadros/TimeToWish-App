package io.github.rubenquadros.timetowish.ui.divider

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.rubenquadros.timetowish.ui.TWTheme

interface TWDivider {
    enum class Orientation {
        HORIZONTAL, VERTICAL;
    }
}

@Composable
fun TWDivider(
    modifier: Modifier = Modifier,
    orientation: TWDivider.Orientation = TWDivider.Orientation.HORIZONTAL
) {
    when (orientation) {
        TWDivider.Orientation.HORIZONTAL -> {
            HorizontalDivider(
                modifier = modifier,
                thickness = TWTheme.borders.width1,
                color = TWTheme.colors.outline
            )
        }

        TWDivider.Orientation.VERTICAL -> {
            VerticalDivider(
                modifier = modifier,
                thickness = TWTheme.borders.width1,
                color = TWTheme.colors.outline
            )
        }
    }
}