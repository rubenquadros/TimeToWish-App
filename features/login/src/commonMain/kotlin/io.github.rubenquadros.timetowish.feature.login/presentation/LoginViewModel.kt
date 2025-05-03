package io.github.rubenquadros.timetowish.feature.login.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import io.github.rubenquadros.timetowish.core.base.usecase.UseCaseResult
import io.github.rubenquadros.timetowish.core.base.viewmodel.BaseViewModel
import io.github.rubenquadros.timetowish.feature.login.domain.entity.LoginData
import io.github.rubenquadros.timetowish.feature.login.domain.entity.LoginKeys
import io.github.rubenquadros.timetowish.feature.login.domain.usecase.GetLoginDataUseCase
import io.github.rubenquadros.timetowish.feature.login.presentation.auth.GoogleLoginDelegate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class LoginViewModel(
    savedStateHandle: SavedStateHandle,
    private val getLoginDataUseCase: GetLoginDataUseCase,
    private val googleLoginDelegate: GoogleLoginDelegate
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
        //first show the login options
        viewModelScope.launch {
            googleLoginDelegate.login(clientId = _loginKeys.google.android)
        }

//        viewModelScope.launch {
//            updateState { state: LoginUiState ->
//                val loginState = (state as? LoginUiState.TokenLoaded)?.loginState
//
//                loginState?.let {
//                    state.copy(loginState = loginState.copy(showLoginLoading = true))
//                } ?: state
//            }
//
//            delay(4000)
//
//            updateState { state: LoginUiState ->
//                val loginState = (state as? LoginUiState.TokenLoaded)?.loginState
//
//                loginState?.let {
//                    state.copy(loginState = loginState.copy(showLoginLoading = false, isLoginSuccess = true))
//                } ?: state
//            }
//        }
    }

    private fun UseCaseResult<LoginData>.updateLoginKeys() {
        if (this is UseCaseResult.Success) {
            _loginKeys = this.data.keys
        }
    }
}