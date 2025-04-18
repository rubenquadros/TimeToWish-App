package io.github.rubenquadros.timetowish.feature.home.domain.entity

import io.github.rubenquadros.timetowish.core.session.CurrentUser
import kotlinx.collections.immutable.ImmutableList

data class HomeEntity(
    val currentUser: CurrentUser?,
    val todayEvents: ImmutableList<Event>?
)