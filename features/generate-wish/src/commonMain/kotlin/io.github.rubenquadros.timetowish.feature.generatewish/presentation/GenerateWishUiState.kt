package io.github.rubenquadros.timetowish.feature.generatewish.presentation

import io.github.rubenquadros.timetowish.feature.generatewish.domain.entity.ChatMessage
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable

@Serializable
internal data class GenerateWishUiState(
    val conversations: ImmutableList<ChatMessage> = persistentListOf()
)
