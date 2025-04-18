package io.github.rubenquadros.timetowish.feature.home.data.mapper

import io.github.rubenquadros.timetowish.feature.home.data.model.EventResponse
import io.github.rubenquadros.timetowish.feature.home.domain.entity.Event

internal fun EventResponse.toEvent(): Event {
    return Event(
        id = id,
        userId = userId,
        date = date,
        title = title,
        description = description,
        day = day,
        month = month,
        reminderId = reminderId,
        reminderDateTime = reminderDateTime
    )
}