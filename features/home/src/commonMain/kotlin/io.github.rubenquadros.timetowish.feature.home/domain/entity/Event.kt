package io.github.rubenquadros.timetowish.feature.home.domain.entity

internal data class Event(
    val id: String,
    val userId: String,
    val date: String,
    val title: String,
    val description: String,
    val day: Int,
    val month: Int,
    val reminderId: Int?,
    val reminderDateTime: String?
)