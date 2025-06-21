package io.github.rubenquadros.timetowish.feature.generatewish.data.repository

import io.github.rubenquadros.timetowish.core.api.ApiManager
import io.github.rubenquadros.timetowish.core.api.body
import io.github.rubenquadros.timetowish.core.base.MemoryDataSource
import io.github.rubenquadros.timetowish.feature.generatewish.data.mapper.toGenerateWishChatMessage
import io.github.rubenquadros.timetowish.feature.generatewish.data.model.Chat
import io.github.rubenquadros.timetowish.feature.generatewish.data.model.ChatResponse
import io.github.rubenquadros.timetowish.feature.generatewish.data.model.Part
import io.github.rubenquadros.timetowish.feature.generatewish.domain.entity.ChatMessage
import io.github.rubenquadros.timetowish.feature.generatewish.domain.repository.GenerateWishRepository
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.appendPathSegments
import org.koin.core.annotation.Single

@Single
internal class GenerateWishRepositoryImpl(
    private val chatDataSource: MemoryDataSource<List<Chat>>,
    private val apiManager: ApiManager
) : GenerateWishRepository {
    override suspend fun generateWish(query: String): ChatMessage.GenerateWishChatMessage {
        //create request
        val chats = chatDataSource.latest().orEmpty().toMutableList().apply {
            add(Chat(role = "user", parts = listOf(Part(query))))
        }

        //make api call
        val response = apiManager.client.post {
            url {
                appendPathSegments("generate-wish")
            }

            setBody(chats)
        }.body<List<ChatResponse>>()

        //add response and save it
        with(response.first().contentResponse) { //intentionally taking first as there should always be a response
            chats.add(Chat(role = role, parts = parts))
        }

        return response.toGenerateWishChatMessage()
    }

    override suspend fun clearChatData() {
        chatDataSource.clear()
    }
}