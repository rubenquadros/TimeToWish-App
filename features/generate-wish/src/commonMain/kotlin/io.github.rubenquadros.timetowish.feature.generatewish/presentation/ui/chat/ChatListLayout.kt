package io.github.rubenquadros.timetowish.feature.generatewish.presentation.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.rubenquadros.timetowish.feature.generatewish.domain.entity.ChatMessage
import io.github.rubenquadros.timetowish.features.generatewish.resources.Res
import io.github.rubenquadros.timetowish.features.generatewish.resources.generate_wish_model_error
import io.github.rubenquadros.timetowish.features.generatewish.resources.generate_wish_retry
import io.github.rubenquadros.timetowish.features.generatewish.resources.generate_wish_retry_cta_accessibility_label
import io.github.rubenquadros.timetowish.ui.TWTheme
import io.github.rubenquadros.timetowish.ui.button.TWButton
import io.github.rubenquadros.timetowish.ui.image.ImageReference
import io.github.rubenquadros.timetowish.ui.loader.TWSkeletonLoader
import io.github.rubenquadros.timetowish.ui.text.TWText
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ChatLayout(
    conversations: ImmutableList<ChatMessage>,
    lazyListState: LazyListState,
    onAction: (action: ChatMessageScope.ChatAction) -> Unit,
    retryChatOnError: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = lazyListState,
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(TWTheme.spacings.space4)
    ) {
        items(
            items = conversations,
            key = { conversation -> conversation.messageId }
        ) { item ->
            when (item) {
                is ChatMessage.GenerateWishChatMessage -> {
                    MessageItem(chatMessage = item, onAction = onAction)
                }

                is ChatMessage.ModelLoading -> {
                    ModelLoadingItem()
                }

                is ChatMessage.Error -> {
                    ModelErrorItem(retryChatOnError = retryChatOnError)
                }
            }
        }
    }
}

@Composable
private fun MessageItem(
    chatMessage: ChatMessage.GenerateWishChatMessage,
    onAction: (action: ChatMessageScope.ChatAction) -> Unit
) {
    val scope = remember(chatMessage) { ChatMessageScope(chatMessage) }

    ChatContentWithActions(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = scope.horizontalAlignment,
        content = {
            scope.RenderMessage()
        },
        actions = {
            scope.RenderActions(onAction = onAction)
        }
    )
}

@Composable
private fun ModelLoadingItem() {
    ModelChatContent(modifier = Modifier.fillMaxWidth()) {
        TWSkeletonLoader(
            variant = TWSkeletonLoader.Variant.TwoLines
        )
    }
}

@Composable
private fun ModelErrorItem(
    retryChatOnError: () -> Unit
) {
    ModelChatContent(modifier = Modifier.fillMaxWidth()) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(TWTheme.spacings.space4),
            verticalAlignment = Alignment.CenterVertically
        ){
            Box(modifier = Modifier
                .background(color = TWTheme.colors.error, shape = TWTheme.shapes.large)
                .weight(1f)
            ) {
                TWText(
                    modifier = Modifier.padding(TWTheme.spacings.space3),
                    text = stringResource(Res.string.generate_wish_model_error),
                    textColor = TWTheme.colors.onError
                )
            }

            TWButton(
                modifier = Modifier.size(TWTheme.spacings.space6),
                variant = TWButton.Variant.TertiaryTinted(color = TWTheme.colors.primary),
                content = TWButton.Content.Icon(
                    imageReference = ImageReference.ResImage(Res.drawable.generate_wish_retry),
                    accessibilityLabel = stringResource(Res.string.generate_wish_retry_cta_accessibility_label)
                ),
                onClick = retryChatOnError
            )
        }
    }
}

