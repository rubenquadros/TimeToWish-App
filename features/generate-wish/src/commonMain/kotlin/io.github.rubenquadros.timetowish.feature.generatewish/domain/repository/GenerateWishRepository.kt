package io.github.rubenquadros.timetowish.feature.generatewish.domain.repository

import io.github.rubenquadros.timetowish.feature.generatewish.domain.entity.ChatMessage

internal interface GenerateWishRepository {
    suspend fun generateWish(query: String) : ChatMessage.GenerateWishChatMessage

    suspend fun clearChatData()
}