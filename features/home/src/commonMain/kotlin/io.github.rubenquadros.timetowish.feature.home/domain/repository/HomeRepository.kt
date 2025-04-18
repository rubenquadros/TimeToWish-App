package io.github.rubenquadros.timetowish.feature.home.domain.repository

import io.github.rubenquadros.timetowish.feature.home.domain.entity.Event

internal interface HomeRepository {
    suspend fun getTodayEvents(date: String): List<Event>
}