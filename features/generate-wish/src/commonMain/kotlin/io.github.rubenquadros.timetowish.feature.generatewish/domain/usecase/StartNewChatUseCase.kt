package io.github.rubenquadros.timetowish.feature.generatewish.domain.usecase

import io.github.rubenquadros.timetowish.core.base.BaseUseCase
import io.github.rubenquadros.timetowish.feature.generatewish.domain.repository.GenerateWishRepository
import org.koin.core.annotation.Factory

internal abstract class StartNewChatUseCase : BaseUseCase<Unit, Unit>()

@Factory
internal class StartNewChatUseCaseImpl(
    private val generateWishRepository: GenerateWishRepository
) : StartNewChatUseCase() {
    override suspend fun execute(request: Unit) {
        generateWishRepository.clearChatData()
    }
}