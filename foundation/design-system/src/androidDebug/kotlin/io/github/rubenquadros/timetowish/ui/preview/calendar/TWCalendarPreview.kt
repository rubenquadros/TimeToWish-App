package io.github.rubenquadros.timetowish.ui.preview.calendar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import io.github.rubenquadros.timetowish.ui.calendar.TWCalendar
import io.github.rubenquadros.timetowish.ui.preview.TWPreviewTheme
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@PreviewLightDark
@Composable
private fun TWCalendarPreview() {
    TWPreviewTheme {
        Box(
            modifier = Modifier.fillMaxWidth().wrapContentHeight()
        ) {
            val localDateTime = remember {
                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            }

            TWCalendar(
                year = localDateTime.year,
                month = localDateTime.monthNumber,
                onClick = {}
            )
        }
    }
}