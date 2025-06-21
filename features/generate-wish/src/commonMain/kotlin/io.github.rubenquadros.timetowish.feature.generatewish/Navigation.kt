package io.github.rubenquadros.timetowish.feature.generatewish

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.rubenquadros.timetowish.feature.generatewish.presentation.ui.GenerateWishScreen
import io.github.rubenquadros.timetowish.navigation.routes.generatewish.GenerateWishScreen

fun NavGraphBuilder.generateWishScreen(navigateUp: () -> Unit) {
    composable<GenerateWishScreen> {
        GenerateWishScreen(
            navigateUp = navigateUp
        )
    }
}