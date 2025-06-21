package io.github.rubenquadros.timetowish.feature.generatewish.data.mapper

import io.github.rubenquadros.timetowish.feature.generatewish.data.model.ChatResponse
import io.github.rubenquadros.timetowish.feature.generatewish.domain.entity.ChatMessage
import io.github.rubenquadros.timetowish.feature.generatewish.domain.entity.ChatRole

internal fun List<ChatResponse>.toGenerateWishChatMessage() : ChatMessage.GenerateWishChatMessage {
    return with(this.first().contentResponse) {
        ChatMessage.GenerateWishChatMessage(
            role = ChatRole.get(role),
            text = parts.first().text.trim()
        )
    }
}