package io.github.rubenquadros.timetowish.feature.generatewish.presentation.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.rubenquadros.timetowish.feature.generatewish.domain.entity.ChatMessage
import io.github.rubenquadros.timetowish.feature.generatewish.domain.entity.ChatRole
import io.github.rubenquadros.timetowish.features.generatewish.resources.Res
import io.github.rubenquadros.timetowish.features.generatewish.resources.generate_wish_copy
import io.github.rubenquadros.timetowish.features.generatewish.resources.generate_wish_copy_cta_accessibility_label
import io.github.rubenquadros.timetowish.ui.TWTheme
import io.github.rubenquadros.timetowish.ui.image.ImageReference
import io.github.rubenquadros.timetowish.ui.image.TWImage
import io.github.rubenquadros.timetowish.ui.text.TWText
import org.jetbrains.compose.resources.stringResource

@Stable
internal interface ChatMessageScope {
    val horizontalAlignment : Alignment.Horizontal

    @Composable
    fun RenderMessage()

    @Composable
    fun RenderActions(onAction: (action: ChatAction) -> Unit)

    companion object {
        operator fun invoke(message: ChatMessage.GenerateWishChatMessage): ChatMessageScope {
            return if (message.role == ChatRole.USER) UserMessageScope(message)
            else ModelMessageScope(message)
        }
    }

    sealed interface ChatAction {
        data class Copy(val data: String) : ChatAction
        data object Edit : ChatAction
        data object Regenerate : ChatAction
    }
}

internal class UserMessageScope(private val message: ChatMessage.GenerateWishChatMessage) : ChatMessageScope {
    override val horizontalAlignment: Alignment.Horizontal = Alignment.End

    @Composable
    override fun RenderMessage() {
        Box(
            modifier = Modifier
                .wrapContentWidth()
                .padding(start = TWTheme.spacings.space6)
                .background(
                color = TWTheme.colors.surfaceContainer,
                shape = TWTheme.shapes.large
            )
        ) {
            TWText(
                modifier = Modifier.padding(TWTheme.spacings.space3),
                text = message.text,
                textColor = TWTheme.colors.onSurface
            )
        }
    }

    @Composable
    override fun RenderActions(onAction: (action: ChatMessageScope.ChatAction) -> Unit) {
        //edit

        //copy
        ChatActionButton(
            imageReference = ImageReference.ResImage(Res.drawable.generate_wish_copy),
            accessibilityLabel = stringResource(Res.string.generate_wish_copy_cta_accessibility_label)
        ) { onAction(ChatMessageScope.ChatAction.Copy(message.text)) }
    }
}

internal class ModelMessageScope(private val message: ChatMessage.GenerateWishChatMessage) : ChatMessageScope {
    override val horizontalAlignment: Alignment.Horizontal = Alignment.Start

    @Composable
    override fun RenderMessage() {
        TWText(
            text = message.text,
            textColor = TWTheme.colors.onSurface
        )
    }

    @Composable
    override fun RenderActions(onAction: (action: ChatMessageScope.ChatAction) -> Unit) {
        //copy
        ChatActionButton(
            imageReference = ImageReference.ResImage(Res.drawable.generate_wish_copy),
            accessibilityLabel = stringResource(Res.string.generate_wish_copy_cta_accessibility_label)
        ) { onAction(ChatMessageScope.ChatAction.Copy(message.text)) }

        //re-generate
    }
}

@Composable
private fun ChatActionButton(
    imageReference: ImageReference,
    accessibilityLabel: String,
    onClick: () -> Unit,
) {
    TWImage(
        modifier = Modifier
            .size(TWTheme.spacings.space6)
            .clickable(
                onClickLabel = accessibilityLabel,
                onClick = onClick
            )
        ,
        imageReference = imageReference,
        accessibilityLabel = "", //will be announced as button
        tint = TWTheme.colors.primary
    )
}