package io.github.rubenquadros.timetowish.feature.home.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.rubenquadros.timetowish.feature.home.presentation.HomeUiState
import io.github.rubenquadros.timetowish.feature.home.presentation.HomeViewModel
import io.github.rubenquadros.timetowish.feature.home.resources.Res
import io.github.rubenquadros.timetowish.feature.home.resources.home_error_retry_cta
import io.github.rubenquadros.timetowish.shared.ui.ErrorScreen
import io.github.rubenquadros.timetowish.ui.TWTheme
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun HomeScreen(
    homeViewModel: HomeViewModel = koinViewModel()
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TWTheme.colors.surface)
            .verticalScroll(rememberScrollState())
    ) {
        AnimatedVisibility(
            visible = uiState is HomeUiState.Loading,
            enter = enterTransition,
            exit = existTransition
        ) {
            HomeScreenLoading()
        }

        AnimatedVisibility(
            visible = uiState is HomeUiState.Data,
            enter = enterTransition
        ) {
            HomeContent((uiState as HomeUiState.Data).homeEntity)
        }

        AnimatedVisibility(
            visible = uiState is HomeUiState.Error,
            enter = enterTransition,
            exit = existTransition
        ) {
            ErrorScreen(
                modifier = Modifier.fillMaxSize(),
                cta = ErrorScreen.Cta(
                    text = stringResource(Res.string.home_error_retry_cta),
                    onClick = homeViewModel::retryOnError
                )
            )
        }
    }
}

private val existTransition = fadeOut(tween(300))
private val enterTransition = fadeIn(tween(500), initialAlpha = 0.3f)