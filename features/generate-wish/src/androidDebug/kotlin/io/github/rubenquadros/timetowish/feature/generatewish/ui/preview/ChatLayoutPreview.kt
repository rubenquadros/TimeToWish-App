package io.github.rubenquadros.timetowish.feature.generatewish.ui.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.rubenquadros.timetowish.feature.generatewish.presentation.ui.chat.ChatLayout
import io.github.rubenquadros.timetowish.ui.TWTheme
import io.github.rubenquadros.timetowish.ui.preview.TWPreview
import io.github.rubenquadros.timetowish.ui.preview.TWPreviewTheme

@TWPreview
@Composable
private fun ChatLayoutPreview() {
    TWPreviewTheme {
        Box(modifier = Modifier.fillMaxWidth().background(TWTheme.colors.surface)) {
            ChatLayout(
                modifier = Modifier.padding(TWTheme.spacings.space4),
                conversations = getConversations(),
                onAction = {},
                retryChatOnError = {},
                lazyListState = rememberLazyListState()
            )
        }
    }
}