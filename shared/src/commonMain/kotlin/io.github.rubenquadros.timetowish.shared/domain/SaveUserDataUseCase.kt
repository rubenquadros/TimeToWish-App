package io.github.rubenquadros.timetowish.shared.domain

import io.github.rubenquadros.timetowish.core.base.BaseUseCase
import io.github.rubenquadros.timetowish.core.session.CurrentUser
import io.github.rubenquadros.timetowish.shared.data.UserRepository
import org.koin.core.annotation.Factory

abstract class SaveLoggedInUserDataUseCase : BaseUseCase<SaveLoggedInUserDataUseCase.Params, Unit>() {
    data class Params(
        val id: String,
        val name: String,
        val email: String,
        val profilePic: String
    )
}

@Factory
internal class SaveLoggedInUserDataUseCaseImpl(
    private val userRepository: UserRepository
) : SaveLoggedInUserDataUseCase() {
    override suspend fun execute(request: Params) {
        userRepository.saveUser(
            currentUser = with(request) {
                CurrentUser(id = id, name = name, email = email, profilePic = profilePic, isLoggedIn = true)
            }
        )
    }
}