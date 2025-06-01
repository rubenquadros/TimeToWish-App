package io.github.rubenquadros.timetowish.shared.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.role
import io.github.rubenquadros.timetowish.ui.TWTheme
import io.github.rubenquadros.timetowish.ui.image.ImageReference
import io.github.rubenquadros.timetowish.ui.image.TWImage
import io.github.rubenquadros.timetowish.ui.text.TWTitle
import org.jetbrains.compose.resources.stringResource
import timetowish.shared.generated.resources.Res
import timetowish.shared.generated.resources.common_back
import timetowish.shared.generated.resources.common_back_button_accessibility_label

interface TWTopAppBar {

    data class Icon(
        val imageReference: ImageReference,
        val accessibilityLabel: String,
        val onClick: () -> Unit
    )

    data class Title(
        val text: String,
        val position: TitlePosition
    )

    enum class TitlePosition {
        START, CENTER;
    }
}

/**
 * @see [io.github.rubenquadros.timetowish.shared.ui.preview.TWTopAppBarTitlePreview]
 * @see [io.github.rubenquadros.timetowish.shared.ui.preview.TWTopAppBarTitleWithIconPreview]
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TWTopAppBar(
    modifier: Modifier = Modifier,
    title: TWTopAppBar.Title? = null,
    icon: TWTopAppBar.Icon? = null
) {

    if (title != null && title.position == TWTopAppBar.TitlePosition.CENTER) {
        //center aligned
        CenterAlignedTopAppBar(
            modifier = modifier,
            title = { NavTitle(title) },
            navigationIcon = {
                if (icon != null) {
                    NavIcon(icon)
                }
            },
            colors = getTopAppBarColors()
        )
    } else {
        TopAppBar(
            modifier = modifier,
            title = {
                if (title != null) {
                    NavTitle(title)
                }
            },
            navigationIcon = {
                if (icon != null) {
                    NavIcon(icon)
                }
            },
            colors = getTopAppBarColors()
        )
    }
}

@Composable
fun backIcon(onClick: () -> Unit) = TWTopAppBar.Icon(
    imageReference = ImageReference.ResImage(Res.drawable.common_back),
    accessibilityLabel = stringResource(Res.string.common_back_button_accessibility_label),
    onClick = onClick
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun getTopAppBarColors() = TopAppBarColors(
    containerColor = TWTheme.colors.transparent,
    titleContentColor = TWTheme.colors.primary,
    navigationIconContentColor = TWTheme.colors.onSurface,
    actionIconContentColor = TWTheme.colors.onSurface,
    scrolledContainerColor = TWTheme.colors.onSurface
)

@Composable
private fun NavTitle(title: TWTopAppBar.Title) {
    TWTitle(
        title = title.text,
        textColor = TWTheme.colors.primary
    )
}

@Composable
private fun NavIcon(icon: TWTopAppBar.Icon) {
    TWImage(
        modifier = Modifier
            .padding(start = TWTheme.spacings.space2)
            .size(TWTheme.spacings.space16)
            .clickable(onClickLabel = icon.accessibilityLabel, onClick = icon.onClick)
            .clearAndSetSemantics {
                role = Role.Button
            },
        imageReference = icon.imageReference,
        accessibilityLabel = icon.accessibilityLabel,
        tint = TWTheme.colors.onSurface
    )
}