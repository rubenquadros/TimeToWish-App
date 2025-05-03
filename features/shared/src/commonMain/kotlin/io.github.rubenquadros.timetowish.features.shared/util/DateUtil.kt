package io.github.rubenquadros.timetowish.shared.util

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

fun getToday(): String {
    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    return getDateTimeToStringFormatter().format(today)
}

private fun getDateTimeToStringFormatter(): DateTimeFormat<LocalDateTime> {
    return LocalDateTime.Format {
        dayOfMonth()
        char('-')
        monthNumber()
        char('-')
        year()
    }
}