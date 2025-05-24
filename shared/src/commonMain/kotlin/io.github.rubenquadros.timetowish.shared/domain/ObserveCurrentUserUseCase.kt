package io.github.rubenquadros.timetowish.shared.domain

import io.github.rubenquadros.timetowish.core.base.UseCase
import io.github.rubenquadros.timetowish.core.session.CurrentUser
import io.github.rubenquadros.timetowish.shared.data.UserRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

abstract class ObserveCurrentUserUseCase : UseCase<Unit, Flow<CurrentUser>>()

@Factory
internal class ObserveCurrentUserUseCaseImpl(private val userRepository: UserRepository): ObserveCurrentUserUseCase() {
    override fun execute(request: Unit): Flow<CurrentUser> {
        return userRepository.observeCurrentUser()
    }
}