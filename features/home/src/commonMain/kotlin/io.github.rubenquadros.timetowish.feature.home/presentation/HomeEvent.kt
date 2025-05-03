package io.github.rubenquadros.timetowish.feature.home.presentation

import io.github.rubenquadros.timetowish.navigation.routes.TWDestination

internal sealed interface HomeEvent {
    data class Navigate(val destination: TWDestination) : HomeEvent
}