package io.github.rubenquadros.timetowish.feature.login.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.rubenquadros.timetowish.feature.login.presentation.LoginEvent
import io.github.rubenquadros.timetowish.feature.login.presentation.LoginUiState
import io.github.rubenquadros.timetowish.feature.login.presentation.LoginViewModel
import io.github.rubenquadros.timetowish.navigation.routes.NavigateTo
import io.github.rubenquadros.timetowish.navigation.routes.login.LoginRoute.TermsAndPrivacy
import io.github.rubenquadros.timetowish.navigation.routes.withDefaultOptions
import io.github.rubenquadros.timetowish.shared.presentation.ui.enterFadeInTransition
import io.github.rubenquadros.timetowish.shared.presentation.ui.existFadeOutTransition
import io.github.rubenquadros.timetowish.ui.TWTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun LoginScreen(
    loginViewModel: LoginViewModel = koinViewModel(),
    navigateTo: NavigateTo,
    navigateUp: () -> Unit,
) {

    //handle one off events
    LaunchedEffect(Unit) {
        loginViewModel.uiEvent.collect { event ->
            when (event) {
                is LoginEvent.LoginSuccess -> {
                    navigateUp()
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .safeDrawingPadding()
            .fillMaxSize()
            .background(TWTheme.colors.surface)
    ) {

        val uiState by loginViewModel.uiState.collectAsStateWithLifecycle()

        AnimatedVisibility(
            visible = uiState is LoginUiState.InitialTokenLoading,
            enter = enterFadeInTransition,
            exit = existFadeOutTransition
        ) {
            LoginScreenLoading()
        }

        AnimatedVisibility(
            visible = uiState is LoginUiState.TokenLoadingError,
            enter = enterFadeInTransition,
            exit = existFadeOutTransition
        ) {
            LoginScreenError(
                navigateUp = navigateUp,
                onRetry = loginViewModel::reloadInitialData
            )
        }

        AnimatedVisibility(
            visible = uiState is LoginUiState.TokenLoaded,
            enter = enterFadeInTransition,
            exit = existFadeOutTransition
        ) {
            LoginContent(
                loginState = (uiState as LoginUiState.TokenLoaded).loginState,
                onLoginClick = loginViewModel::login,
                navigateUp = navigateUp,
                onViewDetailsClick = {
                    val state = (uiState as? LoginUiState.TokenLoaded) ?: return@LoginContent
                    with(state.pages) {
                        navigateTo.withDefaultOptions(TermsAndPrivacy(termsAndConditions, privacyPolicy))
                    }
                }
            )
        }
    }
}