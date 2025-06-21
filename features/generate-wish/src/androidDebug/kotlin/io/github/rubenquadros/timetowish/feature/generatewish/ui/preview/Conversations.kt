package io.github.rubenquadros.timetowish.feature.generatewish.ui.preview

import io.github.rubenquadros.timetowish.feature.generatewish.domain.entity.ChatMessage
import io.github.rubenquadros.timetowish.feature.generatewish.domain.entity.ChatRole
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal fun getConversations(): ImmutableList<ChatMessage> {
    return persistentListOf(
        ChatMessage.GenerateWishChatMessage(
            role = ChatRole.USER,
            text = "Hey, can you share a birthday wish for my cousin."
        ),

        ChatMessage.GenerateWishChatMessage(
            role = ChatRole.MODEL,
            text = "Happy Birthday, cousin! Hope your day’s packed with fun, laughter, and all your favorite things!"
        ),

        ChatMessage.GenerateWishChatMessage(
            role = ChatRole.USER,
            text = "Can you make it 100-150 words?"
        ),

        ChatMessage.ModelLoading,

        ChatMessage.Error("")
    )
}