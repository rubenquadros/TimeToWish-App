package io.github.rubenquadros.timetowish.ui.preview.textInput

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import io.github.rubenquadros.timetowish.ui.TWTheme
import io.github.rubenquadros.timetowish.ui.button.TWButton
import io.github.rubenquadros.timetowish.ui.image.ImageReference
import io.github.rubenquadros.timetowish.ui.preview.TWPreviewTheme
import io.github.rubenquadros.timetowish.ui.resources.Res
import io.github.rubenquadros.timetowish.ui.resources.compose_multiplatform_logo
import io.github.rubenquadros.timetowish.ui.text.TWText
import io.github.rubenquadros.timetowish.ui.textInput.TWTextInput

@PreviewLightDark
@Composable
private fun TWTextInputPreview(
    @PreviewParameter(TWTextInputOutlinePreviewParameterProvider::class) outline: Boolean
) {
    TextInputPreviewContainer(outline = outline)
}

@PreviewLightDark
@Composable
private fun TWTextInputDisabledPreview(
    @PreviewParameter(TWTextInputOutlinePreviewParameterProvider::class) outline: Boolean
) {
    TextInputPreviewContainer(outline = outline, enabled = false)
}

@PreviewLightDark
@Composable
private fun TWTextInputEndContentPreview(
    @PreviewParameter(TWTextInputOutlinePreviewParameterProvider::class) outline: Boolean
) {
    TextInputPreviewContainer(
        outline = outline,
        endContent = {
            TWButton(
                content = TWButton.Content.Icon(
                    imageReference = ImageReference.ResImage(Res.drawable.compose_multiplatform_logo),
                    accessibilityLabel = ""
                )
            ) { }
        }
    )
}

@PreviewLightDark
@Composable
private fun TWTextInputPlaceholderPreview(
    @PreviewParameter(TWTextInputOutlinePreviewParameterProvider::class) outline: Boolean
) {
    TextInputPreviewContainer(
        outline = outline,
        placeholder = {
            TWText(
                text = "Craft a message...",
                textColor = TWTheme.colors.onDisabled
            )
        }
    )
}

@PreviewLightDark
@Composable
private fun TWTextInputDisabledPlaceholderPreview(
    @PreviewParameter(TWTextInputOutlinePreviewParameterProvider::class) outline: Boolean
) {
    TextInputPreviewContainer(
        outline = outline,
        enabled = false,
        placeholder = {
            TWText(
                text = "Craft a message...",
                textColor = TWTheme.colors.onDisabled
            )
        }
    )
}

@Composable
private fun TextInputPreviewContainer(
    outline: Boolean,
    enabled: Boolean = true,
    startContent: (@Composable () -> Unit)? = null,
    endContent: (@Composable () -> Unit)? = null,
    placeholder: (@Composable () -> Unit)? = null,
) {
    TWPreviewTheme {
        var text by remember { mutableStateOf("") }

        Box(modifier = Modifier.fillMaxWidth().background(TWTheme.colors.surface)) {
            TWTextInput(
                modifier = Modifier.fillMaxWidth().padding(TWTheme.spacings.space4).wrapContentHeight(),
                value = text,
                onValueChange = { text = it },
                outlined = outline,
                startContent = startContent,
                endContent = endContent,
                placeholder = placeholder,
                enabled = enabled
            )
        }
    }
}

private class TWTextInputOutlinePreviewParameterProvider : CollectionPreviewParameterProvider<Boolean>(
    listOf(false, true)
)