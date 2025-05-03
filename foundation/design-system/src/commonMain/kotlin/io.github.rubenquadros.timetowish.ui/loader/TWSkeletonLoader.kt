package io.github.rubenquadros.timetowish.ui.loader

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.progressBarRangeInfo
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.rubenquadros.timetowish.ui.TWTheme

interface TWSkeletonLoader {
    sealed interface Variant {
        data object Title : Variant
        data object SingleLine : Variant
        data object TwoLines : Variant
        data object ThreeLines : Variant
        data class Circle(val size: Dp = DefaultBoxSize) : Variant
        data class Box(val width: Dp = DefaultBoxSize, val height: Dp = DefaultBoxSize) : Variant
    }
}

/**
 * @see [io.github.rubenquadros.timetowish.ui.preview.loader.TWSkeletonLoaderPreview]
 * @see [io.github.rubenquadros.timetowish.ui.preview.loader.TWSkeletonLoaderColorPreview]
 */
@Composable
fun TWSkeletonLoader(
    variant: TWSkeletonLoader.Variant,
    modifier: Modifier = Modifier,
    color: Color = TWTheme.colors.neutral
) {
    when (variant) {
        TWSkeletonLoader.Variant.Title -> TWLineSkeletonLoaderInternal(
            modifier = modifier.fillMaxWidth(TitleWidth).height(LineHeight),
            color = color
        )

        TWSkeletonLoader.Variant.SingleLine -> TWLineSkeletonLoaderInternal(
            modifier = modifier.fillMaxWidth().height(LineHeight),
            color = color
        )

        TWSkeletonLoader.Variant.TwoLines -> TWMultilineSkeletonLoaderInternal(
            modifier = modifier,
            color = color,
            numberOfLines = 2
        )

        TWSkeletonLoader.Variant.ThreeLines -> TWMultilineSkeletonLoaderInternal(
            modifier = modifier,
            color = color,
            numberOfLines = 3
        )

        is TWSkeletonLoader.Variant.Circle -> TWBoxSkeletonLoaderInternal(
            modifier = modifier,
            width = variant.size,
            height = variant.size,
            color = color,
            radius = variant.size.value
        )

        is TWSkeletonLoader.Variant.Box -> TWBoxSkeletonLoaderInternal(
            modifier = modifier,
            width = variant.width,
            height = variant.height,
            color = color,
            radius = null
        )
    }
}

@Composable
private fun TWMultilineSkeletonLoaderInternal(
    modifier: Modifier,
    color: Color,
    numberOfLines: Int
) {
    Column(
        modifier = modifier.background(
            color = TWTheme.colors.transparent,
            shape = TWTheme.shapes.extraSmall
        ),
        verticalArrangement = Arrangement.spacedBy(TWTheme.spacings.space2)
    ) {
        when (numberOfLines) {
            2 -> {
                TWLineSkeletonLoaderInternal(
                    modifier = Modifier.fillMaxWidth(TitleWidth).height(LineHeight),
                    color = color
                )

                TWLineSkeletonLoaderInternal(
                    modifier = Modifier.fillMaxWidth().height(LineHeight),
                    color = color
                )
            }

            3 -> {
                TWLineSkeletonLoaderInternal(
                    modifier = Modifier.fillMaxWidth().height(LineHeight),
                    color = color
                )

                TWLineSkeletonLoaderInternal(
                    modifier = Modifier.fillMaxWidth().height(LineHeight),
                    color = color
                )

                TWLineSkeletonLoaderInternal(
                    modifier = Modifier.fillMaxWidth(TitleWidth).height(LineHeight),
                    color = color
                )
            }
        }
    }
}

@Composable
private fun TWLineSkeletonLoaderInternal(modifier: Modifier, color: Color) {
    val transition = rememberInfiniteTransition()
    val transitionAnimation by transition.animateFloat(
        initialValue = 0f,
        targetValue = (Duration + WidthOfBrush).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = Duration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val cornerRadius = with(LocalDensity.current) { TWTheme.spacings.space1.toPx() }

    Box(
        modifier = modifier
            .background(color = color, shape = TWTheme.shapes.extraSmall)
            .shimmer(
                color = color,
                cornerRadius = cornerRadius,
                progress = { transitionAnimation },
                radius = null
            )
            .semantics { progressBarRangeInfo = ProgressBarRangeInfo.Indeterminate }
    )
}

@Composable
private fun TWBoxSkeletonLoaderInternal(
    modifier: Modifier,
    width: Dp,
    height: Dp,
    color: Color,
    radius: Float?
) {
    val transition = rememberInfiniteTransition()
    val transitionAnimation by transition.animateFloat(
        initialValue = 0f,
        targetValue = (Duration + WidthOfBrush).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = Duration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val cornerRadius = with(LocalDensity.current) { TWTheme.spacings.space1.toPx() }

    Box(
        modifier = modifier
            .width(width)
            .height(height)
            .shimmer(
                color = color,
                cornerRadius = cornerRadius,
                progress = { transitionAnimation },
                radius = radius
            )
            .semantics { progressBarRangeInfo = ProgressBarRangeInfo.Indeterminate }
    )
}

private fun Modifier.shimmer(progress: () -> Float, cornerRadius: Float, color: Color, radius: Float?): Modifier {
    return this then ShimmerNodeElement(progress, cornerRadius, color, radius)
}

private data class ShimmerNodeElement(
    private val progress: () -> Float,
    private val cornerRadius: Float,
    private val color: Color,
    private val radius: Float?
) : ModifierNodeElement<ShimmerNode>() {
    override fun create(): ShimmerNode = ShimmerNode(progress = progress, cornerRadius = cornerRadius, color = color, radius = radius)

    override fun update(node: ShimmerNode) {
        node.progress = progress
        node.cornerRadius = cornerRadius
        node.color = color
        node.radius = radius
    }
}

private class ShimmerNode(
    var progress: () -> Float,
    var cornerRadius: Float,
    var color: Color,
    var radius: Float? = null
) : Modifier.Node(), DrawModifierNode {
    override fun ContentDrawScope.draw() {
        val brush = Brush.linearGradient(
            colors = listOf(
                color.copy(alpha = NeutralAlpha),
                Color.White.copy(alpha = ShineAlpha),
                color.copy(alpha = NeutralAlpha)
            ),
            start = Offset(x = progress() - WidthOfBrush, y = 0f),
            end = Offset(x = progress(), y = 0f)
        )

        drawContent()

        if (radius != null) {
            drawCircle(brush = brush)
        } else {
            drawRoundRect(brush = brush, cornerRadius = CornerRadius(cornerRadius))
        }
    }
}

private val LineHeight: Dp = 16.dp
private val DefaultBoxSize: Dp = 48.dp
private const val TitleWidth = 0.7f
private const val NeutralAlpha = 0.6f
private const val ShineAlpha = 0.4f
private const val Duration = 1000
private const val WidthOfBrush = 500