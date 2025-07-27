package io.github.rubenquadros.timetowish.feature.landing

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.rubenquadros.timetowish.feature.home.homeScreen
import io.github.rubenquadros.timetowish.navigation.routes.NavigateTo
import io.github.rubenquadros.timetowish.navigation.routes.TWDestination
import io.github.rubenquadros.timetowish.navigation.routes.home.HomeScreen
import io.github.rubenquadros.timetowish.navigation.routes.withDefaultOptions
import io.github.rubenquadros.timetowish.ui.TWTheme
import io.github.rubenquadros.timetowish.ui.image.TWImage
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun LandingScreen(
    navigateTo: NavigateTo,
    landingViewModel: LandingViewModel = koinViewModel()
) {

    val navController = rememberNavController()

    //handle one-off events
    LaunchedEffect(Unit) {
        landingViewModel.uiEvent.collect { event ->
            when (event) {
                is LandingScreenNavEvent.TabNavigation -> {
                    navController.navigate(event.route)
                }

                is LandingScreenNavEvent.FeatureNavigation -> {
                    navigateTo.withDefaultOptions(event.route)
                }
            }
        }
    }

    LandingScreenContent(
        navController = navController,
        navigateTo = navigateTo,
        onClick = landingViewModel::onClick
    )
}

/**
 * @see [io.github.rubenquadros.timetowish.feature.landing.preview.LandingScreenContentPreview]
 */
@Composable
internal fun LandingScreenContent(
    navController: NavHostController,
    navigateTo: NavigateTo,
    onClick: (destination:TWDestination) -> Unit,
) {

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = TWTheme.colors.surfaceContainer,
                contentColor = TWTheme.colors.onSurface
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()

                bottomDestinations().forEach { item ->
                    val selected = item.destination::class.qualifiedName == navBackStackEntry?.destination?.route

                    NavigationBarItem(
                        selected = selected,
                        colors = NavigationBarItemColors(
                            unselectedIconColor = TWTheme.colors.onSurface,
                            selectedIconColor = TWTheme.colors.onSecondaryContainer,
                            selectedTextColor = TWTheme.colors.onPrimaryContainer,
                            unselectedTextColor = TWTheme.colors.onSurface,
                            selectedIndicatorColor = TWTheme.colors.secondaryContainer,
                            disabledIconColor = TWTheme.colors.disabled,
                            disabledTextColor = TWTheme.colors.disabled
                        ),
                        icon = {
                            TWImage(
                                modifier = Modifier.size(TWTheme.spacings.space12),
                                imageReference = if (selected) item.selectedIcon else item.icon,
                                accessibilityLabel = item.accessibilityLabel,
                            )
                        },
                        onClick = {
                            if (item.destination::class.qualifiedName != navController.currentDestination?.route) {
                                onClick(item.destination)
                            }
                        }
                    )
                }
            }
        }
    ) { contentPadding ->
        NavHost(
            modifier = Modifier.padding(contentPadding),
            navController = navController,
            startDestination = HomeScreen,
        ) {
            homeScreen(navigateTo)
        }
    }
}