package io.github.rubenquadros.timetowish.feature.login.ui.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import io.github.rubenquadros.timetowish.feature.login.presentation.LoginState
import io.github.rubenquadros.timetowish.feature.login.presentation.ui.LoginContent
import io.github.rubenquadros.timetowish.ui.TWTheme
import io.github.rubenquadros.timetowish.ui.preview.TWPreview
import io.github.rubenquadros.timetowish.ui.preview.TWPreviewTheme

@TWPreview
@Composable
private fun LoginContentPreview(
    @PreviewParameter(LoginStatePreviewParameterProvider::class) loginState: LoginState
) {
    TWPreviewTheme {
        PreviewLoginContent {
            LoginContent(
                loginState = loginState,
                onLoginClick = {},
                navigateUp = {},
                onViewDetailsClick = {}
            )
        }
    }
}

@Composable
private fun PreviewLoginContent(
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize().background(TWTheme.colors.surface)) {
        content()
    }
}

private class LoginStatePreviewParameterProvider : CollectionPreviewParameterProvider<LoginState>(
    listOf(
        LoginState(),
        LoginState(
            showLoginLoading = true,
            showLoginError = false,
            isLoginSuccess = false
        ),
        LoginState(
            showLoginLoading = false,
            showLoginError = true,
            isLoginSuccess = false
        ),
        LoginState(
            showLoginLoading = false,
            showLoginError = false,
            isLoginSuccess = true
        )
    )
)