package io.github.rubenquadros.timetowish.feature.login.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import io.github.rubenquadros.timetowish.core.base.usecase.UseCaseResult
import io.github.rubenquadros.timetowish.core.base.viewmodel.BaseViewModel
import io.github.rubenquadros.timetowish.core.session.getPlatform
import io.github.rubenquadros.timetowish.feature.login.domain.entity.LoginData
import io.github.rubenquadros.timetowish.feature.login.domain.entity.LoginKeys
import io.github.rubenquadros.timetowish.feature.login.domain.usecase.GetLoginDataUseCase
import io.github.rubenquadros.timetowish.feature.login.domain.usecase.ValidateLoginAndSaveUserUseCase
import io.github.rubenquadros.timetowish.services.auth.Auth
import io.github.rubenquadros.timetowish.services.auth.SignInResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class LoginViewModel(
    savedStateHandle: SavedStateHandle,
    private val getLoginDataUseCase: GetLoginDataUseCase,
    private val validateLoginAndSaveUserUseCase: ValidateLoginAndSaveUserUseCase,
    private val googleLoginDelegate: Auth
) : BaseViewModel<LoginUiState, LoginEvent>(
    savedStateHandle = savedStateHandle,
    initialState = LoginUiState.InitialTokenLoading
) {
    private lateinit var _loginKeys: LoginKeys

    override fun loadInitialData(): Flow<LoginUiState> {
        return getLoginDataUseCase(Unit)
            .onEach { result ->
                result.updateLoginKeys()
            }.map { result ->
                if (result is UseCaseResult.Success) {
                    LoginUiState.TokenLoaded(pages = result.data.pages)
                } else {
                    LoginUiState.TokenLoadingError
                }
            }
    }

    fun login() {
        viewModelScope.launch {
            //reset login state
            resetLoginState()

            //first show the login options and get the token
            val loginResult = googleLoginDelegate.login(
                clientId = getClientId(),
                serverId = _loginKeys.google.server
            )

            if (loginResult is SignInResult.Success) {
                //update login loading state and validate with the server
                updateLoginLoadingState()

                val result = validateLoginAndSaveUserUseCase(
                    ValidateLoginAndSaveUserUseCase.Params(loginResult.token)
                )

                if (result is UseCaseResult.Success) {
                    //update the state
                    updateLoginSuccessState()
                    delay(SUCCESS_FEEDBACK)
                    updateEvent(LoginEvent.LoginSuccess)
                } else {
                    updateLoginFailState()
                }
            } else {
                updateLoginFailState()
            }
        }
    }

    private fun resetLoginState() {
        updateState { state ->
            val loginState = (state as? LoginUiState.TokenLoaded)?.loginState

            loginState?.let {
                state.copy(
                    loginState = loginState.copy(
                        showLoginLoading = false,
                        showLoginError = false,
                        isLoginSuccess = false
                    )
                )
            } ?: state
        }
    }

    private fun updateLoginLoadingState() {
        updateState { state ->
            val loginState = (state as? LoginUiState.TokenLoaded)?.loginState

            loginState?.let {
                state.copy(
                    loginState = loginState.copy(
                        showLoginLoading = true,
                        showLoginError = false,
                        isLoginSuccess = false
                    )
                )
            } ?: state
        }
    }

    private fun updateLoginFailState() {
        updateState { state ->
            val loginState = (state as? LoginUiState.TokenLoaded)?.loginState
            loginState?.let {
                state.copy(
                    loginState = loginState.copy(
                        showLoginLoading = false,
                        showLoginError = true,
                        isLoginSuccess = false
                    )
                )
            } ?: state
        }
    }

    private fun updateLoginSuccessState() {
        updateState { state ->
            val loginState = (state as? LoginUiState.TokenLoaded)?.loginState
            loginState?.let {
                state.copy(
                    loginState = loginState.copy(
                        showLoginLoading = false,
                        showLoginError = false,
                        isLoginSuccess = true
                    )
                )
            } ?: state
        }
    }

    private fun UseCaseResult<LoginData>.updateLoginKeys() {
        if (this is UseCaseResult.Success) {
            _loginKeys = this.data.keys
        }
    }

    private fun getClientId(): String {
        return if (getPlatform() == "android") {
            _loginKeys.google.android
        } else {
            _loginKeys.google.ios
        }
    }
}

private const val SUCCESS_FEEDBACK = 300L