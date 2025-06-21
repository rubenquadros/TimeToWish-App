package io.github.rubenquadros.timetowish.feature.generatewish.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.rubenquadros.timetowish.core.base.StateContainer
import io.github.rubenquadros.timetowish.core.base.UseCaseResult
import io.github.rubenquadros.timetowish.feature.generatewish.domain.entity.ChatMessage
import io.github.rubenquadros.timetowish.feature.generatewish.domain.usecase.GenerateWishUseCase
import io.github.rubenquadros.timetowish.feature.generatewish.domain.usecase.StartNewChatUseCase
import io.github.rubenquadros.timetowish.feature.generatewish.presentation.ui.chat.ChatMessageScope
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class GenerateWishViewModel(
    private val generateWishUseCase: GenerateWishUseCase,
    private val startNewChatUseCase: StartNewChatUseCase,
) : ViewModel() {

    private val generateWishContainer = StateContainer<GenerateWishUiState, GenerateWishUiEvent>(
        scope = viewModelScope,
        initialState = GenerateWishUiState()
    )

    val uiState by generateWishContainer::uiState
    val uiEvent by generateWishContainer::uiEvent

    fun submitQuery(query: String) {
        viewModelScope.launch {
            //add user chat and show loading
            addUserChatShowLoading(query)

            //make api call to get chat response
            val result = generateWish(query)

            //remove loading and show the response
            removeLoadingShowChatOrError(query = query, result = result)
        }
    }

    fun startNewChat() {
        viewModelScope.launch {
            //reset state
            generateWishContainer.updateState { state ->
                state.copy(conversations = persistentListOf())
            }.also {
                startNewChatUseCase(Unit)
            }
        }
    }

    fun retryChatOnError() {
        viewModelScope.launch {
            //get user query
            val queryIndex = uiState.value.conversations.lastIndex-1
            val query = (uiState.value.conversations[queryIndex] as? ChatMessage.GenerateWishChatMessage)?.text ?: return@launch

            //remove error and show loading
            removeErrorShowLoading()

            //make api call to get chat response
            val result = generateWish(query)

            //remove loading and show the response
            removeLoadingShowChatOrError(query = query, result = result)
        }
    }

    fun onAction(chatAction: ChatMessageScope.ChatAction) {
        when (chatAction) {
            is ChatMessageScope.ChatAction.Copy -> {
                generateWishContainer.updateEvent(GenerateWishUiEvent.CopyToClipBoard(chatAction.data))
            }
            else -> {
                //TODO::Add handling
            }
        }
    }

    private suspend fun generateWish(query: String) = generateWishUseCase(
        GenerateWishUseCase.Params(query = query)
    )

    private fun addUserChatShowLoading(query: String) {
        generateWishContainer.updateState { state ->
            val updatedList = state.conversations.toMutableList()
            updatedList.apply {
                //remove error if any
                removeAll { it is ChatMessage.Error }
                add(ChatMessage.GenerateWishChatMessage.generateUserMessage(query))
                add(ChatMessage.ModelLoading)
            }

            state.copy(conversations = updatedList.toImmutableList())
        }
    }

    private fun removeLoadingShowChatOrError(
        query: String,
        result: UseCaseResult<ChatMessage.GenerateWishChatMessage>
    ) {
        generateWishContainer.updateState { state ->
            val updatedList = state.conversations.toMutableList()

            val message = if (result is UseCaseResult.Success) {
                result.data
            } else {
                ChatMessage.Error(query)
            }

            updatedList.apply {
                remove(ChatMessage.ModelLoading)
                add(message)
            }

            state.copy(conversations = updatedList.toImmutableList())
        }
    }

    private fun removeErrorShowLoading() {
        generateWishContainer.updateState { state ->
            val updatedList = state.conversations.toMutableList()

            updatedList.apply {
                removeAll { it is ChatMessage.Error }
                add(ChatMessage.ModelLoading)
            }

            state.copy(conversations = updatedList.toImmutableList())
        }
    }
}