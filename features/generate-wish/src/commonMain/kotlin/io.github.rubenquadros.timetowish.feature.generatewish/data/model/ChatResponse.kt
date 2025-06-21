package io.github.rubenquadros.timetowish.feature.generatewish.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ChatResponse(
    @SerialName("content")
    val contentResponse: ContentResponse,
    val finishReason: String,
    val avgLogprobs: Double
)

@Serializable
internal data class ContentResponse(
    val role: String,
    val parts: List<Part>
)