package io.github.rubenquadros.timetowish

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material.navigation.ModalBottomSheetLayout
import androidx.compose.material.navigation.rememberBottomSheetNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import io.github.rubenquadros.timetowish.core.imageLoader.getImageLoader
import io.github.rubenquadros.timetowish.feature.home.HomeScreen
import io.github.rubenquadros.timetowish.feature.home.homeScreen
import io.github.rubenquadros.timetowish.ui.TWTheme

@OptIn(ExperimentalCoilApi::class)
@Composable
fun TWApp() {
    setSingletonImageLoaderFactory { context ->
        getImageLoader(context)
    }

    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberNavController(bottomSheetNavigator)

    TWTheme {
        ModalBottomSheetLayout(
            bottomSheetNavigator = bottomSheetNavigator,
            modifier = Modifier.background(color = TWTheme.colors.surface).safeDrawingPadding().fillMaxSize()
        ) {
            NavHost(
                navController = navController,
                startDestination = HomeScreen
            ) {
                homeScreen()
            }
        }
    }
}