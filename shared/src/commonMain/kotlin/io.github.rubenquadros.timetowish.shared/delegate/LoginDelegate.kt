package io.github.rubenquadros.timetowish.shared.delegate

import io.github.rubenquadros.timetowish.core.base.usecase.UseCaseResult
import io.github.rubenquadros.timetowish.shared.domain.GetIsLoggedInUseCase
import org.koin.core.annotation.Factory

@Factory
class LoginDelegate(private val getIsLoggedInUseCase: GetIsLoggedInUseCase) {

    suspend fun checkLoginState(
        onLoggedIn: () -> Unit,
        onNotLoggedIn: () -> Unit
    ) {
        val isLoggedInResult = getIsLoggedInUseCase(Unit)
        val isLoggedIn = (isLoggedInResult as? UseCaseResult.Success)?.data ?: false

        if (isLoggedIn) {
            onLoggedIn()
        } else {
            onNotLoggedIn()
        }
    }
}