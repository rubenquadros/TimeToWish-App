package io.github.rubenquadros.timetowish.shared.domain

import io.github.rubenquadros.timetowish.core.base.BaseUseCase
import io.github.rubenquadros.timetowish.shared.data.UserRepository
import org.koin.core.annotation.Factory

abstract class GetIsLoggedInUseCase : BaseUseCase<Unit, Boolean>()

@Factory
internal class GetIsLoggedInUseCaseImpl(private val userRepository: UserRepository) : GetIsLoggedInUseCase() {
    override suspend fun execute(request: Unit): Boolean {
        return userRepository.getIsUserLoggedIn()
    }
}