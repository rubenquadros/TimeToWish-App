package io.github.rubenquadros.timetowish.feature.generatewish.presentation.ui.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.rubenquadros.timetowish.ui.TWTheme

@Composable
internal fun ModelChatContent(
    modifier: Modifier,
    content: @Composable () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
}

@Composable
internal fun ChatContentWithActions(
    modifier: Modifier,
    horizontalAlignment: Alignment.Horizontal,
    content: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment,
        verticalArrangement = Arrangement.spacedBy(TWTheme.spacings.space2)
    ) {
        content()

        Row(
            horizontalArrangement = Arrangement.spacedBy(TWTheme.spacings.space2)
        ) { actions() }
    }
}