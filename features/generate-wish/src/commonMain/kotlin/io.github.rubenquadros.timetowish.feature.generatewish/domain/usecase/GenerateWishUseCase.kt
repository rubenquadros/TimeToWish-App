package io.github.rubenquadros.timetowish.feature.generatewish.domain.usecase

import io.github.rubenquadros.timetowish.core.base.BaseUseCase
import io.github.rubenquadros.timetowish.feature.generatewish.domain.entity.ChatMessage
import io.github.rubenquadros.timetowish.feature.generatewish.domain.repository.GenerateWishRepository
import org.koin.core.annotation.Factory

internal abstract class GenerateWishUseCase : BaseUseCase<GenerateWishUseCase.Params, ChatMessage.GenerateWishChatMessage>() {
    data class Params(
        val query: String
    )
}

@Factory
internal class GenerateWishUseCaseImpl(
    private val generateWishRepository: GenerateWishRepository
) : GenerateWishUseCase() {
    override suspend fun execute(request: Params): ChatMessage.GenerateWishChatMessage {
        return generateWishRepository.generateWish(
            query = request.query
        )
    }
}

