package io.github.rubenquadros.timetowish.shared.presentation.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.progressBarRangeInfo
import androidx.compose.ui.semantics.semantics
import io.github.rubenquadros.timetowish.ui.TWTheme
import io.github.rubenquadros.timetowish.ui.image.ImageReference
import io.github.rubenquadros.timetowish.ui.image.TWImage
import org.jetbrains.compose.resources.stringResource
import timetowish.shared.generated.resources.Res
import timetowish.shared.generated.resources.common_loader_accessibility_label
import timetowish.shared.generated.resources.love_loading

/**
 * @see [io.github.rubenquadros.timetowish.shared.ui.preview.LoaderPreview]
 */
@Composable
fun Loader(modifier: Modifier = Modifier) {

    val a11yLabel = stringResource(Res.string.common_loader_accessibility_label)

    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    BoxWithConstraints(
        modifier = modifier
            .semantics {
                progressBarRangeInfo = ProgressBarRangeInfo.Indeterminate
                contentDescription = a11yLabel
            }
    ) {
        val density = LocalDensity.current
        val screenWidthPx = with(density) { maxWidth.toPx() }

        Box(
            modifier = Modifier.matchParentSize().background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        TWTheme.colors.transparent.copy(alpha = 0.25f),
                        TWTheme.colors.transparent
                    ),
                    radius = screenWidthPx
                )
            ),
            contentAlignment = Alignment.Center
        ) {
            TWImage(
                modifier = Modifier
                    .size(TWTheme.spacings.space12)
                    .graphicsLayer {
                        rotationZ = angle
                        scaleX = scale
                        scaleY = scale
                    }
                    .clearAndSetSemantics { }, //do not call out in talkback
                imageReference = ImageReference.ResImage(Res.drawable.love_loading),
                accessibilityLabel = stringResource(Res.string.common_loader_accessibility_label)
            )
        }
    }
}