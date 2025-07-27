package io.github.rubenquadros.timetowish.feature.landing

import io.github.rubenquadros.timetowish.navigation.routes.TWDestination

sealed interface LandingScreenNavEvent {
    data class TabNavigation(val route: TWDestination) : LandingScreenNavEvent

    data class FeatureNavigation(val route: TWDestination) : LandingScreenNavEvent
}