package io.github.rubenquadros.timetowish.feature.generatewish.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.rubenquadros.timetowish.feature.generatewish.presentation.GenerateWishUiEvent
import io.github.rubenquadros.timetowish.feature.generatewish.presentation.GenerateWishUiState
import io.github.rubenquadros.timetowish.feature.generatewish.presentation.GenerateWishViewModel
import io.github.rubenquadros.timetowish.feature.generatewish.presentation.ui.chat.ChatLayout
import io.github.rubenquadros.timetowish.feature.generatewish.presentation.ui.chat.ChatMessageScope
import io.github.rubenquadros.timetowish.features.generatewish.resources.Res
import io.github.rubenquadros.timetowish.features.generatewish.resources.generate_wish_empty_state
import io.github.rubenquadros.timetowish.features.generatewish.resources.generate_wish_new_chat
import io.github.rubenquadros.timetowish.features.generatewish.resources.generate_wish_new_chat_accessibility_label
import io.github.rubenquadros.timetowish.features.generatewish.resources.generate_wish_powered_by
import io.github.rubenquadros.timetowish.shared.presentation.ui.TWTopAppBar
import io.github.rubenquadros.timetowish.shared.presentation.ui.backIcon
import io.github.rubenquadros.timetowish.shared.presentation.ui.enterFadeInTransition
import io.github.rubenquadros.timetowish.shared.presentation.ui.exitFadeOutTransition
import io.github.rubenquadros.timetowish.ui.TWTheme
import io.github.rubenquadros.timetowish.ui.button.TWButton
import io.github.rubenquadros.timetowish.ui.image.ImageReference
import io.github.rubenquadros.timetowish.ui.text.TWText
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun GenerateWishScreen(
    navigateUp: () -> Unit,
    generateWishViewModel: GenerateWishViewModel = koinViewModel()
) {
    //TODO::Migrate to Clipboard once all the targets have the functionality
    val clipboard = LocalClipboardManager.current

    //handle one-off events
    LaunchedEffect(Unit) {
        generateWishViewModel.uiEvent.collect { event ->
            when (event) {
                is GenerateWishUiEvent.CopyToClipBoard -> {
                    clipboard.setText(AnnotatedString(event.data))
                }
            }
        }
    }

    val uiState by generateWishViewModel.uiState.collectAsStateWithLifecycle()

    GenerateWishContent(
        generateWishUiState = uiState,
        navigateUp = navigateUp,
        onSubmitQuery = generateWishViewModel::submitQuery,
        onNewChatClick = generateWishViewModel::startNewChat,
        onAction = generateWishViewModel::onAction,
        retryChatOnError = generateWishViewModel::retryChatOnError
    )
}

/**
 * @see [io.github.rubenquadros.timetowish.feature.generatewish.ui.preview.GenerateWishContentPreview]
 */
@Composable
internal fun GenerateWishContent(
    generateWishUiState: GenerateWishUiState,
    navigateUp: () -> Unit,
    onSubmitQuery: (query: String) -> Unit,
    onNewChatClick: () -> Unit,
    onAction: (action: ChatMessageScope.ChatAction) -> Unit,
    retryChatOnError: () -> Unit
) {

    //scroll to last message everytime there is a new message
    val state = rememberLazyListState()
    LaunchedEffect(generateWishUiState.hashCode()) {
        state.animateScrollToItem(index = generateWishUiState.conversations.lastIndex.coerceAtLeast(0))
    }

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .displayCutoutPadding()
            .fillMaxSize()
            .background(TWTheme.colors.surface),
    ) {
        //top bar
        TWTopAppBar(
            icon = backIcon { navigateUp() },
            actions = {
                TWButton(
                    content = TWButton.Content.Icon(
                        imageReference = ImageReference.ResImage(Res.drawable.generate_wish_new_chat),
                        accessibilityLabel = stringResource(Res.string.generate_wish_new_chat_accessibility_label)
                    ),
                    variant = TWButton.Variant.Tertiary,
                    onClick = onNewChatClick
                )
            }
        )

        Spacer(modifier = Modifier.height(TWTheme.spacings.space4))

        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = TWTheme.spacings.space4)
            .weight(1f)
        ) {
            androidx.compose.animation.AnimatedVisibility(
                modifier = Modifier.align(Alignment.Center),
                visible = generateWishUiState.conversations.isEmpty(),
                enter = enterFadeInTransition,
                exit = exitFadeOutTransition
            ) {
                EmptyChatContent()
            }

            androidx.compose.animation.AnimatedVisibility(
                modifier = Modifier.align(Alignment.TopCenter),
                visible = generateWishUiState.conversations.isNotEmpty()
            ) {
                ChatLayout(
                    conversations = generateWishUiState.conversations,
                    onAction = onAction,
                    retryChatOnError = retryChatOnError,
                    lazyListState = state
                )
            }
        }

        Spacer(modifier = Modifier.height(TWTheme.spacings.space4))

        //input layout
        WishQueryInput(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    shape = RoundedCornerShape(
                        topStart = TWTheme.spacings.space4,
                        topEnd = TWTheme.spacings.space4
                    ),
                    color = TWTheme.colors.surfaceContainer
                ),
            onSubmitQuery = onSubmitQuery
        )
    }
}

@Composable
private fun EmptyChatContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TWText(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.generate_wish_empty_state),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TWTheme.spacings.space1))

        TWText(
            modifier = Modifier.fillMaxWidth(),
            text =stringResource(Res.string.generate_wish_powered_by),
            textColor = TWTheme.colors.onDisabled,
            textStyle = TWTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}