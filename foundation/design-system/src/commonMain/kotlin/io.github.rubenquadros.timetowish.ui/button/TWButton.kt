package io.github.rubenquadros.timetowish.ui.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.rubenquadros.timetowish.ui.TWTheme
import io.github.rubenquadros.timetowish.ui.image.ImageReference
import io.github.rubenquadros.timetowish.ui.image.TWImage
import io.github.rubenquadros.timetowish.ui.text.TWText

interface TWButton {
    sealed interface Variant {
        data object Primary : Variant
        data object Secondary : Variant
        data object Tertiary : Variant
        data class TertiaryTinted(val color: Color) : Variant
        data object Elevated : Variant
    }

    sealed interface Content {
        data class Text(val text: String, val icon: TextIcon? = null) : Content
        data class Icon(val imageReference: ImageReference, val accessibilityLabel: String) : Content
    }

    sealed interface PaddingAdjustment {
        data object Default : PaddingAdjustment
        data object AdjustLeft : PaddingAdjustment
        data object AdjustRight : PaddingAdjustment
    }

    @Immutable
    data class TextIcon(
        val imageReference: ImageReference,
        val position: TextIconPosition,
        val accessibilityLabel: String,
        val tint: Color? = null
    )

    enum class TextIconPosition {
        START, END;
    }
}

private val HorizontalPadding = 24.dp
private val VerticalPadding = 8.dp

/**
 * @see [io.github.rubenquadros.timetowish.ui.preview.button.TWTextButtonEnabledPreview]
 * @see [io.github.rubenquadros.timetowish.ui.preview.button.TWTextButtonDisabledPreview]
 * @see [io.github.rubenquadros.timetowish.ui.preview.button.TWIconButtonEnabledPreview]
 * @see [io.github.rubenquadros.timetowish.ui.preview.button.TWIconButtonDisabledPreview]
 */
@Composable
fun TWButton(
    content: TWButton.Content,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    variant: TWButton.Variant = TWButton.Variant.Primary,
    paddingAdjustment: TWButton.PaddingAdjustment = TWButton.PaddingAdjustment.Default,
    onClick: () -> Unit
) {
    when (content) {
        is TWButton.Content.Text -> {
            TextButtonInternal(
                modifier = modifier,
                isEnabled = isEnabled,
                textContent = content,
                paddingAdjustment = paddingAdjustment,
                variant = variant,
                onClick = onClick
            )
        }

        is TWButton.Content.Icon -> {
            IconButtonInternal(
                modifier = modifier,
                isEnabled = isEnabled,
                iconContent = content,
                variant = variant,
                onClick = onClick
            )
        }
    }
}

@Composable
private fun TextButtonInternal(
    modifier: Modifier,
    isEnabled: Boolean,
    textContent: TWButton.Content.Text,
    paddingAdjustment: TWButton.PaddingAdjustment,
    variant: TWButton.Variant,
    onClick: () -> Unit,
) {
    val colors = getButtonColors(variant)

    Button(
        modifier = modifier,
        enabled = isEnabled,
        colors = colors,
        contentPadding = getButtonPadding(paddingAdjustment),
        elevation = getButtonElevation(variant),
        border = getButtonBorder(variant = variant, isEnabled = isEnabled),
        shape = TWTheme.shapes.small,
        onClick = onClick
    ) {
        if (textContent.icon != null) {
            ButtonIconAndTextContentInternal(
                textContent = textContent,
                colors = colors,
                paddingAdjustment = paddingAdjustment,
                isEnabled = isEnabled
            )
        } else {
            ButtonTextContentInternal(
                textContent = textContent,
                colors = colors,
                isEnabled = isEnabled,
                paddingAdjustment = paddingAdjustment
            )
        }
    }
}

@Composable
private fun ButtonIconAndTextContentInternal(
    textContent: TWButton.Content.Text,
    colors: ButtonColors,
    isEnabled: Boolean,
    paddingAdjustment: TWButton.PaddingAdjustment
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(TWTheme.spacings.space2)
    ) {
        if (textContent.icon?.position == TWButton.TextIconPosition.START) {
            TWImage(
                modifier = Modifier
                    .size(TWTheme.spacings.space6)
                    .clearAndSetSemantics {  }, //do not announce in talk back
                imageReference = textContent.icon.imageReference,
                accessibilityLabel = textContent.icon.accessibilityLabel,
                tint = if (!isEnabled) {
                    colors.disabledContentColor
                } else textContent.icon.tint
            )

            ButtonTextContentInternal(
                textContent = textContent,
                colors = colors,
                isEnabled = isEnabled,
                paddingAdjustment = paddingAdjustment
            )
        } else {
            ButtonTextContentInternal(
                textContent = textContent,
                colors = colors,
                isEnabled = isEnabled,
                paddingAdjustment = paddingAdjustment
            )

            TWImage(
                modifier = Modifier.size(TWTheme.spacings.space6),
                imageReference = textContent.icon!!.imageReference,
                accessibilityLabel = textContent.icon.accessibilityLabel,
                tint = if (!isEnabled) {
                    colors.disabledContentColor
                } else null
            )
        }
    }
}

@Composable
private fun ButtonTextContentInternal(
    textContent: TWButton.Content.Text,
    isEnabled: Boolean,
    colors: ButtonColors,
    paddingAdjustment: TWButton.PaddingAdjustment
) {
    TWText(
        text = textContent.text,
        textColor = if (isEnabled) {
            colors.contentColor
        } else {
            colors.disabledContentColor
        },
        textAlign = when (paddingAdjustment) {
            is TWButton.PaddingAdjustment.AdjustLeft -> TextAlign.Left
            is TWButton.PaddingAdjustment.AdjustRight -> TextAlign.Right
            is TWButton.PaddingAdjustment.Default -> TextAlign.Center
        }
    )
}

@Composable
private fun IconButtonInternal(
    modifier: Modifier,
    isEnabled: Boolean,
    iconContent: TWButton.Content.Icon,
    variant: TWButton.Variant,
    onClick: () -> Unit
) {
    val colors = getIconButtonColors(variant)
    val tint = getIconButtonTint(variant = variant, isEnabled = isEnabled)

    OutlinedIconButton(
        modifier = modifier.then(
            if (variant is TWButton.Variant.Elevated) {
                Modifier.shadow(
                    elevation = TWTheme.elevations.level2,
                    shape = TWTheme.shapes.full,
                    ambientColor = TWTheme.colors.surfaceTint,
                    spotColor = TWTheme.colors.surfaceTint
                )
            } else {
                Modifier
            }
        ),
        enabled = isEnabled,
        colors = colors,
        border = getButtonBorder(variant = variant, isEnabled = isEnabled),
        onClick = onClick,
        shape = TWTheme.shapes.full
    ) {
        TWImage(
            modifier = Modifier.clip(TWTheme.shapes.full),
            imageReference = iconContent.imageReference,
            accessibilityLabel = iconContent.accessibilityLabel,
            tint = tint
        )
    }
}

@Composable
private fun getButtonColors(variant: TWButton.Variant): ButtonColors {
    return when (variant) {
        TWButton.Variant.Primary -> {
            ButtonColors(
                containerColor = TWTheme.colors.primary,
                contentColor = TWTheme.colors.onPrimary,
                disabledContainerColor = TWTheme.colors.disabled,
                disabledContentColor = TWTheme.colors.onDisabled
            )
        }

        TWButton.Variant.Secondary -> {
            ButtonColors(
                containerColor = TWTheme.colors.transparent,
                contentColor = TWTheme.colors.primary,
                disabledContainerColor = TWTheme.colors.disabledVariant,
                disabledContentColor = TWTheme.colors.onDisabled
            )
        }

        TWButton.Variant.Tertiary -> {
            ButtonColors(
                containerColor = TWTheme.colors.transparent,
                contentColor = TWTheme.colors.primary,
                disabledContainerColor = TWTheme.colors.transparent,
                disabledContentColor = TWTheme.colors.onDisabled
            )
        }

        is TWButton.Variant.TertiaryTinted -> {
            ButtonColors(
                containerColor = TWTheme.colors.transparent,
                contentColor = variant.color,
                disabledContainerColor = TWTheme.colors.transparent,
                disabledContentColor = TWTheme.colors.onDisabled
            )
        }

        TWButton.Variant.Elevated -> {
            ButtonColors(
                containerColor = TWTheme.colors.transparent,
                contentColor = TWTheme.colors.primary,
                disabledContainerColor = TWTheme.colors.transparent,
                disabledContentColor = TWTheme.colors.onDisabled
            )
        }
    }
}

@Composable
private fun getButtonElevation(variant: TWButton.Variant): ButtonElevation {
    return when (variant) {
        TWButton.Variant.Elevated -> {
            ButtonDefaults.buttonElevation(
                defaultElevation = TWTheme.elevations.level1,
                pressedElevation = TWTheme.elevations.level1,
                focusedElevation = TWTheme.elevations.level1,
                hoveredElevation = TWTheme.elevations.level1,
                disabledElevation = TWTheme.elevations.level1
            )
        }

        else -> {
            ButtonDefaults.buttonElevation()
        }
    }
}

private fun getButtonPadding(paddingAdjustment: TWButton.PaddingAdjustment): PaddingValues {
    return when (paddingAdjustment) {
        is TWButton.PaddingAdjustment.Default -> {
            ButtonDefaults.ContentPadding
        }

        is TWButton.PaddingAdjustment.AdjustLeft -> {
            PaddingValues(
                top = VerticalPadding, bottom = VerticalPadding,
                start = 0.dp, end = HorizontalPadding * 2
            )
        }

        is TWButton.PaddingAdjustment.AdjustRight -> {
            PaddingValues(
                top = VerticalPadding, bottom = VerticalPadding,
                start = HorizontalPadding * 2, end = 0.dp
            )
        }
    }
}

@Composable
private fun getButtonBorder(variant: TWButton.Variant, isEnabled: Boolean): BorderStroke? {
    return when (variant) {
        TWButton.Variant.Secondary -> {
            BorderStroke(
                color = if (isEnabled) {
                    TWTheme.colors.primary
                } else {
                    TWTheme.colors.disabled
                },
                width = TWTheme.borders.width1
            )
        }

        else -> null
    }
}

@Composable
private fun getIconButtonColors(variant: TWButton.Variant): IconButtonColors {
    val buttonColors = getButtonColors(variant)

    return IconButtonColors(
        containerColor = buttonColors.containerColor,
        contentColor = buttonColors.contentColor,
        disabledContainerColor = buttonColors.disabledContainerColor,
        disabledContentColor = buttonColors.disabledContentColor
    )
}

@Composable
private fun getIconButtonTint(variant: TWButton.Variant, isEnabled: Boolean): Color? {
    return when {
        !isEnabled -> TWTheme.colors.onDisabled
        variant is TWButton.Variant.TertiaryTinted -> variant.color
        else -> null
    }
}