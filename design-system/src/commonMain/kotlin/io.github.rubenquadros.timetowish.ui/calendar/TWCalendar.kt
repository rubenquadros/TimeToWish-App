package io.github.rubenquadros.timetowish.ui.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import io.github.rubenquadros.timetowish.ui.TWTheme
import io.github.rubenquadros.timetowish.ui.text.TWText
import kotlinx.datetime.*
import org.jetbrains.compose.resources.stringArrayResource
import timetowish.design_system.generated.resources.Res
import timetowish.design_system.generated.resources.tw_week_days

/**
 * @see [io.github.rubenquadros.timetowish.ui.preview.calendar.TWCalendarPreview]
 */
@Composable
fun TWCalendar(
    year: Int,
    month: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: TWCalendarColors = TWCalendarDefaults.calendarColors(),
    shapes: TWCalendarShapes = TWCalendarDefaults.calendarShapes()
) {
    Column(
        modifier = modifier.fillMaxWidth().wrapContentHeight()
    ) {
        val daysOfWeek = stringArrayResource(Res.array.tw_week_days)

        //Days
        WeekDayLayout {
            daysOfWeek.forEach { day ->
                TWText(
                    modifier = Modifier.wrapContentHeight(),
                    text = day,
                    textAlign = TextAlign.Center,
                    textColor = colors.dayTextColor,
                    textStyle = TWTheme.typography.titleMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(TWTheme.spacings.space1))

        //Number
        WeekDate(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            year = year,
            monthNumber = month,
            colors = colors,
            shapes = shapes,
            onClick = onClick
        )

        Spacer(modifier = Modifier.height(TWTheme.spacings.space1))
    }
}

@Composable
private fun WeekDayLayout(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    val spacing = with(LocalDensity.current) {
        TWTheme.spacings.space1.roundToPx()
    }

    Layout(
        modifier = modifier.fillMaxWidth().wrapContentHeight(),
        content = content
    ) { measurables, constraints ->

        val childWidthWithPadding = (constraints.maxWidth / 7) - (spacing)
        val childConstraints = Constraints(
            minWidth = childWidthWithPadding,
            maxWidth = childWidthWithPadding,
            minHeight = 0,
            maxHeight = constraints.maxHeight
        )

        val placeables = measurables.map { measurable ->
            measurable.measure(childConstraints)
        }

        val height = placeables.getOrNull(0)?.height ?: 50

        layout(constraints.maxWidth, height) {
            placeables.forEachIndexed { index, placeable ->
                val x = index * (childWidthWithPadding + spacing) + spacing / 2
                placeable.place(x, 0)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun WeekDate(
    year: Int,
    monthNumber: Int,
    colors: TWCalendarColors,
    shapes: TWCalendarShapes,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val month = remember(monthNumber) { Month(monthNumber)  }
    val numberOfDays = remember(monthNumber) { monthNumber.monthLength(isLeapYear(year)) }
    val firstDayOfTheMonth = remember(year, month) { LocalDate(year = year, month = month, dayOfMonth = 1) }
    val startDayOfTheWeek = remember(firstDayOfTheMonth) { firstDayOfTheMonth.dayOfWeek }

    FlowRow(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(TWTheme.spacings.space2)
    ) {
        repeat(startDayOfTheWeek.getInitialSkipDays()) {
            WeekDateLayout {
                Box(
                    modifier = Modifier.background(color = TWTheme.colors.transparent).wrapContentHeight()
                )
            }
        }

        repeat(numberOfDays) {
            val day = it+1 //it starts from 0
            val isCurrentDate = isCurrentDate(monthNumber, day)
            WeekDateLayout {
                Box(
                    modifier = Modifier
                        .background(
                            shape = shapes.dateContainerShape,
                            color = if (isCurrentDate) colors.currentDateContainerColor else colors.dateContainerColor
                        )
                        .wrapContentHeight()
                        .clickable(enabled = true, onClick = onClick)
                ) {
                    TWText(
                        modifier = Modifier.padding(TWTheme.spacings.space2).align(Alignment.Center),
                        text = day.toString(),
                        textColor = if (isCurrentDate) colors.currentDateTextColor else colors.dateTextColor
                    )
                }
            }
        }
    }
}

@Composable
private fun WeekDateLayout(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val spacing = with(LocalDensity.current) {
        TWTheme.spacings.space3.roundToPx()
    }

    Layout(
        modifier = modifier.wrapContentSize(),
        content = content
    ) { measurables, constraints ->

        val childWidthWithoutPadding = constraints.maxWidth / 7
        val childWidthWithPadding = childWidthWithoutPadding - spacing
        val childHeight = childWidthWithPadding.toFloat() * 1.2f
        val childConstraints = Constraints(
            minWidth = childWidthWithPadding,
            maxWidth = childWidthWithPadding,
            minHeight = childHeight.toInt(),
            maxHeight = constraints.maxHeight
        )

        val placeables = measurables.map { measurable ->
            measurable.measure(childConstraints)
        }

        layout(childWidthWithoutPadding, childHeight.toInt()) {
            placeables.forEachIndexed { index, placeable ->
                val x = index * (childWidthWithPadding + spacing) + spacing / 2
                placeable.place(x, 0)
            }
        }
    }
}

/**
 * Initial number of tiles to be skipped
 */
private fun DayOfWeek.getInitialSkipDays(): Int {
    return when (this) {
        DayOfWeek.SUNDAY -> 0
        DayOfWeek.MONDAY -> 1
        DayOfWeek.TUESDAY -> 2
        DayOfWeek.WEDNESDAY -> 3
        DayOfWeek.THURSDAY -> 4
        DayOfWeek.FRIDAY -> 5
        DayOfWeek.SATURDAY -> 6
        else -> -1
    }
}

private fun isCurrentDate(month: Int, day: Int): Boolean {
    val localDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val currentMonth = localDateTime.monthNumber

    if (currentMonth != month) return false

    return localDateTime.dayOfMonth == day
}