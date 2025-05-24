package io.github.rubenquadros.timetowish.feature.login.domain.usecase

import io.github.rubenquadros.timetowish.core.api.ApiException
import io.github.rubenquadros.timetowish.core.base.BaseUseCase
import io.github.rubenquadros.timetowish.core.base.UseCaseResult
import io.github.rubenquadros.timetowish.shared.domain.SaveLoggedInUserDataUseCase
import org.koin.core.annotation.Factory

abstract class ValidateLoginAndSaveUserUseCase : BaseUseCase<ValidateLoginAndSaveUserUseCase.Params, Boolean>() {
    data class Params(val token: String)
}

@Factory
internal class ValidateLoginAndSaveUserUseCaseImpl(
    private val validateLoginUseCase: ValidateLoginUseCase,
    private val saveLoggedInUserDataUseCase: SaveLoggedInUserDataUseCase,
) : ValidateLoginAndSaveUserUseCase() {

    override suspend fun execute(request: Params): Boolean {
        val loginResult = validateLoginUseCase(ValidateLoginUseCase.Params(request.token))

        if (loginResult is UseCaseResult.Success) {
            with(loginResult.data) {
                saveLoggedInUserDataUseCase(
                    SaveLoggedInUserDataUseCase.Params(id, name, email, profilePic.orEmpty())
                )
            }
            return true
        } else {
            val message = (loginResult as? UseCaseResult.Error)?.message ?: "Unknown error"
            throw ApiException(message)
        }
    }
}
