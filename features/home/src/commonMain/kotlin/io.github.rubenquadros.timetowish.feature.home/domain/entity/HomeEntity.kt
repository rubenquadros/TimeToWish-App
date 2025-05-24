package io.github.rubenquadros.timetowish.feature.home.domain.entity

import io.github.rubenquadros.timetowish.core.SerializableImmutableList
import io.github.rubenquadros.timetowish.core.session.CurrentUser
import kotlinx.serialization.Serializable

@Serializable
internal data class HomeEntity(
    val currentUser: CurrentUser?,
    val todayEvents: SerializableImmutableList<Event>?
)