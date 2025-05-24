package io.github.rubenquadros.timetowish.feature.login.domain.usecase

import io.github.rubenquadros.timetowish.core.base.BaseUseCase
import io.github.rubenquadros.timetowish.feature.login.domain.entity.LoggedInUser
import io.github.rubenquadros.timetowish.feature.login.domain.repository.LoginRepository
import org.koin.core.annotation.Factory

internal abstract class ValidateLoginUseCase : BaseUseCase<ValidateLoginUseCase.Params, LoggedInUser>() {
    data class Params(val token: String)
}

@Factory
internal class ValidateLoginUseCaseImpl(
    private val loginRepository: LoginRepository
) : ValidateLoginUseCase() {
    override suspend fun execute(request: Params): LoggedInUser {
        return loginRepository.validateLogin(request.token)
    }
}