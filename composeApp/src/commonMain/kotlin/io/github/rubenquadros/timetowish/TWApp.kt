package io.github.rubenquadros.timetowish

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.navigation.ModalBottomSheetLayout
import androidx.compose.material.navigation.rememberBottomSheetNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import io.github.rubenquadros.timetowish.core.imageLoader.getImageLoader
import io.github.rubenquadros.timetowish.feature.home.homeScreen
import io.github.rubenquadros.timetowish.feature.login.loginScreen
import io.github.rubenquadros.timetowish.navigation.routes.home.HomeScreen
import io.github.rubenquadros.timetowish.navigation.routes.safeNavigate
import io.github.rubenquadros.timetowish.ui.TWTheme
import org.koin.compose.KoinApplication

@OptIn(ExperimentalCoilApi::class)
@Composable
fun TWApp() {
    setSingletonImageLoaderFactory { platformContext ->
        getImageLoader(platformContext)
    }

    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberNavController(bottomSheetNavigator)

    KoinApplication(
        application = koinConfig().config
    ) {
        TWTheme {
            ModalBottomSheetLayout(
                bottomSheetNavigator = bottomSheetNavigator,
                modifier = Modifier.background(color = TWTheme.colors.surface).fillMaxSize(),
                sheetShape = RoundedCornerShape(topStart = TWTheme.spacings.space6, topEnd = TWTheme.spacings.space6),
                sheetBackgroundColor = TWTheme.colors.surfaceContainer,
            ) {
                NavHost(
                    navController = navController,
                    startDestination = HomeScreen
                ) {
                    homeScreen { destination, navOptionsBuilder ->
                        navController.safeNavigate(destination, navOptionsBuilder)
                    }

                    loginScreen(navigateUp = { navController.navigateUp() }) { destination, navOptionsBuilder ->
                        navController.safeNavigate(destination, navOptionsBuilder)
                    }
                }
            }
        }
    }
}