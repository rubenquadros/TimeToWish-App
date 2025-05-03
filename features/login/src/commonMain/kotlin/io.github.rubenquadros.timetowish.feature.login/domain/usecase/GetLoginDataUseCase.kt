package io.github.rubenquadros.timetowish.feature.login.domain.usecase

import io.github.rubenquadros.timetowish.core.base.usecase.BaseFlowUseCase
import io.github.rubenquadros.timetowish.feature.login.domain.entity.LoginData
import io.github.rubenquadros.timetowish.feature.login.domain.repository.LoginRepository
import org.koin.core.annotation.Factory

internal abstract class GetLoginDataUseCase : BaseFlowUseCase<Unit, LoginData>()

@Factory
internal class GetLoginDataUseCaseImpl(
    private val loginRepository: LoginRepository
) : GetLoginDataUseCase() {
    override suspend fun execute(request: Unit): LoginData {
        return loginRepository.getLoginData()
    }
}