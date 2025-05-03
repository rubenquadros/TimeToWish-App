package io.github.rubenquadros.timetowish.feature.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.rubenquadros.timetowish.feature.home.presentation.ui.HomeScreen
import io.github.rubenquadros.timetowish.navigation.routes.NavigateTo
import io.github.rubenquadros.timetowish.navigation.routes.home.HomeScreen

fun NavGraphBuilder.homeScreen(navigateTo: NavigateTo) {
    composable<HomeScreen> {
        HomeScreen(navigateTo = navigateTo)
    }
}

