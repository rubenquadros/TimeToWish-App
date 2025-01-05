package io.github.rubenquadros.timetowish.feature.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.rubenquadros.timetowish.core.destination.TWDestination
import io.github.rubenquadros.timetowish.feature.home.presentation.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
data object HomeScreen: TWDestination

fun NavGraphBuilder.homeScreen() {
    composable<HomeScreen> {
        HomeScreen()
    }
}

