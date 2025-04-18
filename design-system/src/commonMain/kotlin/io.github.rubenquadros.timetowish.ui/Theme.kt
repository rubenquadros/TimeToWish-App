package io.github.rubenquadros.timetowish.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.rubenquadros.timetowish.ui.theme.*

private val LocalSpacings = staticCompositionLocalOf {
    TWSpacings(
        spaceHalf = Dp.Unspecified,
        space1 = Dp.Unspecified,
        space2 = Dp.Unspecified,
        space3 = Dp.Unspecified,
        space4 = Dp.Unspecified,
        space6 = Dp.Unspecified,
        space8 = Dp.Unspecified,
        space12 = Dp.Unspecified,
        space16 = Dp.Unspecified,
        space24 = Dp.Unspecified
    )
}

private val LocalElevations = staticCompositionLocalOf {
    TWElevations(
        none = Dp.Unspecified,
        level1 = Dp.Unspecified,
        level2 = Dp.Unspecified,
        level3 = Dp.Unspecified,
        level4 = Dp.Unspecified,
        level5 = Dp.Unspecified
    )
}

private val LocalBorders = staticCompositionLocalOf {
    TWBorders(
        none = Dp.Unspecified,
        width1 = Dp.Unspecified,
        width2 = Dp.Unspecified,
        width3 = Dp.Unspecified,
        width4 = Dp.Unspecified,
        width5 = Dp.Unspecified
    )
}

private val LocalShapes = staticCompositionLocalOf {
    TWShapes(
        none = RoundedCornerShape(ZeroCornerSize),
        extraSmall = RoundedCornerShape(ZeroCornerSize),
        small = RoundedCornerShape(ZeroCornerSize),
        medium = RoundedCornerShape(ZeroCornerSize),
        large = RoundedCornerShape(ZeroCornerSize),
        extraLarge = RoundedCornerShape(ZeroCornerSize),
        full = RoundedCornerShape(ZeroCornerSize)
    )
}

private val LocalColors = compositionLocalOf {
    TWColors(
        primary = Color.Unspecified,
        onPrimary = Color.Unspecified,
        primaryContainer = Color.Unspecified,
        onPrimaryContainer = Color.Unspecified,
        secondary = Color.Unspecified,
        onSecondary = Color.Unspecified,
        secondaryContainer = Color.Unspecified,
        onSecondaryContainer = Color.Unspecified,
        tertiary = Color.Unspecified,
        onTertiary = Color.Unspecified,
        tertiaryContainer = Color.Unspecified,
        onTertiaryContainer = Color.Unspecified,
        disabled = Color.Unspecified,
        disabledVariant = Color.Unspecified,
        onDisabled = Color.Unspecified,
        error = Color.Unspecified,
        onError = Color.Unspecified,
        errorContainer = Color.Unspecified,
        onErrorContainer = Color.Unspecified,
        surface = Color.Unspecified,
        surfaceContainer = Color.Unspecified,
        surfaceTint = Color.Unspecified,
        onSurface = Color.Unspecified,
        onSurfaceVariant = Color.Unspecified,
        neutral = Color.Unspecified,
        outline = Color.Unspecified,
        white = Color.Unspecified,
        transparent = Color.Unspecified
    )
}

private val LocalTypography = staticCompositionLocalOf {
    TWTypography(
        displayLarge = TextStyle.Default,
        displayMedium = TextStyle.Default,
        displaySmall = TextStyle.Default,
        headlineLarge = TextStyle.Default,
        headlineMedium = TextStyle.Default,
        headlineSmall = TextStyle.Default,
        titleLarge = TextStyle.Default,
        titleMedium = TextStyle.Default,
        titleSmall = TextStyle.Default,
        labelLarge = TextStyle.Default,
        labelMedium = TextStyle.Default,
        labelSmall = TextStyle.Default,
        bodyLarge = TextStyle.Default,
        bodyMedium = TextStyle.Default,
        bodySmall = TextStyle.Default
    )
}

@Composable
fun TWTheme(
    content: @Composable () -> Unit
) {
    val spacings = TWSpacings(
        spaceHalf = 2.dp,
        space1 = 4.dp,
        space2 = 8.dp,
        space3 = 12.dp,
        space4 = 16.dp,
        space6 = 24.dp,
        space8 = 32.dp,
        space12 = 48.dp,
        space16 = 64.dp,
        space24 = 96.dp
    )

    val elevations = TWElevations(
        none = 0.dp,
        level1 = 1.dp,
        level2 = 3.dp,
        level3 = 6.dp,
        level4 = 8.dp,
        level5 = 12.dp
    )

    val borders = TWBorders(
        none = 0.dp,
        width1 = 1.dp,
        width2 = 2.dp,
        width3 = 4.dp,
        width4 = 8.dp,
        width5 = 16.dp
    )

    val shapes = TWShapes(
        none = RoundedCornerShape(0.dp),
        extraSmall = RoundedCornerShape(4.dp),
        small = RoundedCornerShape(8.dp),
        medium = RoundedCornerShape(12.dp),
        large = RoundedCornerShape(16.dp),
        extraLarge = RoundedCornerShape(28.dp),
        full = CircleShape
    )

    val twColorScheme = if (isSystemInDarkTheme()) {
        TWDarkColorScheme
    } else {
        TWLightColorScheme
    }

    val colorScheme = if (isSystemInDarkTheme()) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    val twType = typography()

    val typography = TWTypography(
        displayLarge = twType.displayLarge,
        displayMedium = twType.displayMedium,
        displaySmall = twType.displaySmall,
        headlineLarge = twType.headlineLarge,
        headlineMedium = twType.headlineMedium,
        headlineSmall = twType.headlineSmall,
        titleLarge = twType.titleLarge,
        titleMedium = twType.titleMedium,
        titleSmall = twType.titleSmall,
        labelLarge = twType.labelLarge,
        labelMedium = twType.labelMedium,
        labelSmall = twType.labelSmall,
        bodyLarge = twType.bodyLarge,
        bodyMedium = twType.bodyMedium,
        bodySmall = twType.bodySmall
    )

    CompositionLocalProvider(
        LocalSpacings provides spacings,
        LocalElevations provides elevations,
        LocalBorders provides borders,
        LocalShapes provides shapes,
        LocalColors provides twColorScheme,
        LocalTypography provides typography
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography()
        ) {
            content()
        }
    }
}

object TWTheme {
    val spacings: TWSpacings
        @Composable
        get() = LocalSpacings.current

    val elevations: TWElevations
        @Composable
        get() = LocalElevations.current

    val borders: TWBorders
        @Composable
        get() = LocalBorders.current

    val shapes: TWShapes
        @Composable
        get() = LocalShapes.current

    val colors: TWColors
        @Composable
        get() = LocalColors.current

    val typography: TWTypography
        @Composable
        get() = LocalTypography.current
}