package io.github.rubenquadros.timetowish.feature.home.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.rubenquadros.timetowish.feature.home.presentation.HomeEvent
import io.github.rubenquadros.timetowish.feature.home.presentation.HomeUiState
import io.github.rubenquadros.timetowish.feature.home.presentation.HomeViewModel
import io.github.rubenquadros.timetowish.feature.home.resources.Res
import io.github.rubenquadros.timetowish.feature.home.resources.home_error_retry_cta
import io.github.rubenquadros.timetowish.navigation.routes.NavigateTo
import io.github.rubenquadros.timetowish.navigation.routes.withDefaultOptions
import io.github.rubenquadros.timetowish.shared.presentation.ui.ErrorScreen
import io.github.rubenquadros.timetowish.shared.presentation.ui.enterFadeInTransition
import io.github.rubenquadros.timetowish.shared.presentation.ui.exitFadeOutTransition
import io.github.rubenquadros.timetowish.ui.TWTheme
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun HomeScreen(
    homeViewModel: HomeViewModel = koinViewModel(),
    navigateTo: NavigateTo
) {

    //handle one-off events
    LaunchedEffect(Unit) {
        homeViewModel.uiEvent.collect { event ->
            when (event) {
                is HomeEvent.Navigate -> {
                    navigateTo.withDefaultOptions(event.destination)
                }
            }
        }
    }

    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TWTheme.colors.surface)
            .verticalScroll(rememberScrollState())
    ) {
        AnimatedVisibility(
            visible = uiState is HomeUiState.Loading,
            enter = enterFadeInTransition,
            exit = exitFadeOutTransition
        ) {
            HomeScreenLoading()
        }

        AnimatedVisibility(
            visible = uiState is HomeUiState.Data,
            enter = enterFadeInTransition
        ) {
            HomeContent(
                homeEntity = (uiState as HomeUiState.Data).homeEntity,
                onCardClick = homeViewModel::onCardClick
            )
        }

        AnimatedVisibility(
            visible = uiState is HomeUiState.Error,
            enter = enterFadeInTransition,
            exit = exitFadeOutTransition
        ) {
            ErrorScreen(
                modifier = Modifier.fillMaxSize(),
                cta = ErrorScreen.Cta(
                    text = stringResource(Res.string.home_error_retry_cta),
                    onClick = homeViewModel::reloadInitialData
                )
            )
        }
    }
}