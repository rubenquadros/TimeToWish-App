package io.github.rubenquadros.timetowish.feature.home.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class GetEventResponse(
    val events: List<EventResponse>
)

@Serializable
internal data class EventResponse(
    val id: String,
    val userId: String,
    val date: String,
    val title: String,
    val description: String,
    val updatedOn: String,
    val day: Int,
    val month: Int,
    val reminderId: Int? = null,
    val reminderDateTime: String? = null,
    val isActive: String
)
