package io.github.rubenquadros.timetowish.feature.generatewish.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class Chat(
    val role: String,
    val parts: List<Part>
)

@Serializable
data class Part(
    val text: String
)