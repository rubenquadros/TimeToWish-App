package io.github.rubenquadros.timetowish.feature.login.presentation

import io.github.rubenquadros.timetowish.feature.login.domain.entity.LoginPages
import io.github.rubenquadros.timetowish.feature.login.resources.Res
import io.github.rubenquadros.timetowish.feature.login.resources.login_google
import io.github.rubenquadros.timetowish.feature.login.resources.login_google_cta
import io.github.rubenquadros.timetowish.feature.login.resources.login_loading
import io.github.rubenquadros.timetowish.feature.login.resources.login_success
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

@Serializable
internal sealed interface LoginUiState {
    @Serializable
    data object InitialTokenLoading : LoginUiState

    @Serializable
    data object TokenLoadingError : LoginUiState

    @Serializable
    data class TokenLoaded(
        val pages: LoginPages,
        val loginState: LoginState = LoginState()
    ) : LoginUiState
}

@Serializable
internal data class LoginState(
    val showLoginLoading: Boolean = false,
    val showLoginError: Boolean = false,
    val isLoginSuccess: Boolean = false
) {

    fun getLoginButtonText(): StringResource {
        return when {
            showLoginLoading -> Res.string.login_loading
            isLoginSuccess -> Res.string.login_success
            else -> Res.string.login_google_cta
        }
    }

    fun getLoginButtonImage(): DrawableResource {
        return when {
            isLoginSuccess -> Res.drawable.login_success
            else -> Res.drawable.login_google
        }
    }
}
