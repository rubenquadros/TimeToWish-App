package io.github.rubenquadros.timetowish.feature.generatewish.domain.entity

import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Serializable
internal sealed class ChatMessage(val messageId: String) {
    @Serializable
    data class GenerateWishChatMessage(
        val role: ChatRole,
        val text: String,
    ) : ChatMessage(Uuid.random().toString()) {
        companion object {
            fun generateUserMessage(query: String): GenerateWishChatMessage {
                return GenerateWishChatMessage(
                    role = ChatRole.USER,
                    text = query
                )
            }
        }
    }

    data object ModelLoading : ChatMessage(Uuid.random().toString())

    data class Error(val query: String) : ChatMessage(Uuid.random().toString())
}

@Serializable
internal enum class ChatRole(val role: String) {
    MODEL("model"), USER("user"), UNKNOWN("unknown");

    companion object {
        fun get(role: String): ChatRole {
            return when (role) {
                MODEL.role -> MODEL
                USER.role -> USER
                else -> UNKNOWN
            }
        }
    }
}
