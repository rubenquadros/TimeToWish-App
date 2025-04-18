package io.github.rubenquadros.timetowish.feature.home.data.repository

import io.github.rubenquadros.timetowish.core.api.ApiManager
import io.github.rubenquadros.timetowish.core.api.body
import io.github.rubenquadros.timetowish.feature.home.data.mapper.toEvent
import io.github.rubenquadros.timetowish.feature.home.data.model.EventResponse
import io.github.rubenquadros.timetowish.feature.home.data.model.GetEventResponse
import io.github.rubenquadros.timetowish.feature.home.domain.entity.Event
import io.github.rubenquadros.timetowish.feature.home.domain.repository.HomeRepository
import io.ktor.client.request.get
import io.ktor.http.path
import org.koin.core.annotation.Single

@Single
internal class HomeRepositoryImpl(private val apiManager: ApiManager): HomeRepository {
    override suspend fun getTodayEvents(date: String): List<Event> {
        val response: GetEventResponse = apiManager.client.get {
            url {
                path(date)
            }
        }.body()

        return response.events.map { eventResponse: EventResponse ->
            eventResponse.toEvent()
        }
    }
}