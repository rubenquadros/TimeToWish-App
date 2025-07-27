package io.github.rubenquadros.timetowish.feature.landing

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.rubenquadros.timetowish.navigation.routes.NavigateTo
import io.github.rubenquadros.timetowish.navigation.routes.landing.LandingScreen

fun NavGraphBuilder.landingScreen(navigateTo: NavigateTo) {
    composable<LandingScreen> {
        LandingScreen(navigateTo)
    }
}