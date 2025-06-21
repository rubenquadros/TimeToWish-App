package io.github.rubenquadros.timetowish.feature.generatewish.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import io.github.rubenquadros.timetowish.features.generatewish.resources.Res
import io.github.rubenquadros.timetowish.features.generatewish.resources.generate_wish_input_hint
import io.github.rubenquadros.timetowish.features.generatewish.resources.generate_wish_send
import io.github.rubenquadros.timetowish.features.generatewish.resources.generate_wish_send_query_cta_accessibility_label
import io.github.rubenquadros.timetowish.ui.TWTheme
import io.github.rubenquadros.timetowish.ui.button.TWButton
import io.github.rubenquadros.timetowish.ui.image.ImageReference
import io.github.rubenquadros.timetowish.ui.text.TWText
import io.github.rubenquadros.timetowish.ui.textInput.TWTextInput
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun WishQueryInput(
    modifier: Modifier = Modifier,
    onSubmitQuery: (query: String) -> Unit,
) {

    var text by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val controller = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        controller?.show()
    }

    Box(modifier = modifier) {
        TWTextInput(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = TWTheme.spacings.space1)
                .focusRequester(focusRequester),
            outlined = false,
            value = text,
            onValueChange = { text = it },
            placeholder = {
                TWText(
                    text = stringResource(Res.string.generate_wish_input_hint),
                    textColor = TWTheme.colors.onDisabled
                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Send
            ),
            keyboardActions = KeyboardActions(
                onSend = {
                    controller?.hide()
                    onSubmitQuery(text)
                    text = ""
                }
            ),
            endContent = {
                if (text.isNotEmpty()) {
                    SubmitButton {
                        controller?.hide()
                        onSubmitQuery(text)
                        text = ""
                    }
                } else Unit
            }
        )
    }
}

@Composable
private fun SubmitButton(
    onClick: () -> Unit
) {
    TWButton(
        modifier = Modifier.padding(horizontal = TWTheme.spacings.space1),
        variant = TWButton.Variant.Tertiary,
        content = TWButton.Content.Icon(
            imageReference = ImageReference.ResImage(Res.drawable.generate_wish_send),
            accessibilityLabel = stringResource(Res.string.generate_wish_send_query_cta_accessibility_label)
        ),
        onClick = onClick
    )
}