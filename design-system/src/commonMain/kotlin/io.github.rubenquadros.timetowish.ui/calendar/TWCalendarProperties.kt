package io.github.rubenquadros.timetowish.ui.calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import io.github.rubenquadros.timetowish.ui.TWTheme

@Immutable
data class TWCalendarColors(
    val dayTextColor: Color,
    val dateContainerColor: Color,
    val currentDateContainerColor: Color,
    val dateTextColor: Color,
    val currentDateTextColor: Color,
    val eventContainerColor: Color
)

@Immutable
data class TWCalendarShapes(
    val dateContainerShape: Shape,
    val eventContainerShape: Shape
)

object TWCalendarDefaults {
    @Composable
    fun calendarColors(): TWCalendarColors = TWCalendarColors(
        dayTextColor = TWTheme.colors.primary,
        dateContainerColor = TWTheme.colors.primaryContainer,
        currentDateContainerColor = TWTheme.colors.error,
        dateTextColor = TWTheme.colors.onPrimaryContainer,
        currentDateTextColor = TWTheme.colors.onError,
        eventContainerColor = TWTheme.colors.tertiary
    )

    @Composable
    fun calendarShapes(): TWCalendarShapes = TWCalendarShapes(
        dateContainerShape = TWTheme.shapes.extraSmall,
        eventContainerShape = TWTheme.shapes.full
    )
}
