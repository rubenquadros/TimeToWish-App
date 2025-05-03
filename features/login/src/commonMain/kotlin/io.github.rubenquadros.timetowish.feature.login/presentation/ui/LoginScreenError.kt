package io.github.rubenquadros.timetowish.feature.login.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.rubenquadros.timetowish.feature.login.resources.Res
import io.github.rubenquadros.timetowish.feature.login.resources.login_error_retry_cta
import io.github.rubenquadros.timetowish.shared.presentation.ui.ErrorScreen
import io.github.rubenquadros.timetowish.shared.presentation.ui.TWTopAppBar
import io.github.rubenquadros.timetowish.shared.presentation.ui.backIcon
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun LoginScreenError(
    onRetry: () -> Unit,
    navigateUp: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {

        TWTopAppBar(
            icon = backIcon { navigateUp() },
        )

        ErrorScreen(
            modifier = Modifier.fillMaxSize(),
            cta = ErrorScreen.Cta(
                text = stringResource(Res.string.login_error_retry_cta),
                onClick = onRetry
            )
        )


    }
}