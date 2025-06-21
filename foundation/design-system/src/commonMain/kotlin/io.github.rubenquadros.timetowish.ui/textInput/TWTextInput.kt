package io.github.rubenquadros.timetowish.ui.textInput

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import io.github.rubenquadros.timetowish.ui.TWTheme

/**
 * @see [io.github.rubenquadros.timetowish.ui.preview.textInput.TWTextInputPreview]
 */
@Composable
fun TWTextInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    outlined: Boolean = false,
    textStyle: TextStyle = TWTheme.typography.bodyLarge,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    startContent: (@Composable () -> Unit)? = null,
    endContent: (@Composable () -> Unit)? = null,
    placeholder: (@Composable () -> Unit)? = null,
) {
    if (outlined) {
        TWOutlinedTextInputInternal(
            modifier = modifier,
            value = value,
            onValueChange = onValueChange,
            textStyle = textStyle,
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
            maxLines = maxLines,
            minLines = minLines,
            startContent = startContent,
            endContent = endContent,
            placeholder = placeholder,
            enabled = enabled
        )
    } else {
        TWTextInputInternal(
            modifier = modifier,
            value = value,
            onValueChange = onValueChange,
            textStyle = textStyle,
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
            maxLines = maxLines,
            minLines = minLines,
            startContent = startContent,
            endContent = endContent,
            placeholder = placeholder,
            enabled = enabled
        )
    }
}

@Composable
private fun TWTextInputInternal(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle,
    enabled: Boolean,
    keyboardActions: KeyboardActions,
    keyboardOptions: KeyboardOptions,
    maxLines: Int,
    minLines: Int,
    startContent: (@Composable () -> Unit)?,
    endContent: (@Composable () -> Unit)?,
    placeholder: (@Composable () -> Unit)?,
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        textStyle = textStyle,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        maxLines = maxLines,
        minLines = minLines,
        leadingIcon = startContent,
        trailingIcon = endContent,
        placeholder = placeholder,
        enabled = enabled,
        shape = TWTheme.shapes.small,
        colors = getTextInputColors()
    )
}

@Composable
private fun TWOutlinedTextInputInternal(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle,
    enabled: Boolean,
    keyboardActions: KeyboardActions,
    keyboardOptions: KeyboardOptions,
    maxLines: Int,
    minLines: Int,
    startContent: (@Composable () -> Unit)?,
    endContent: (@Composable () -> Unit)?,
    placeholder: (@Composable () -> Unit)?,
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        textStyle = textStyle,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        maxLines = maxLines,
        minLines = minLines,
        leadingIcon = startContent,
        trailingIcon = endContent,
        placeholder = placeholder,
        enabled = enabled,
        shape = TWTheme.shapes.small,
        colors = getOutlinedTextInputColors()
    )
}

@Composable
private fun getTextInputColors(): TextFieldColors {
    return TextFieldDefaults.colors(
        focusedContainerColor = TWTheme.colors.surfaceContainer,
        unfocusedContainerColor = TWTheme.colors.surfaceContainer,
        cursorColor = TWTheme.colors.onSurface,
        focusedIndicatorColor = TWTheme.colors.transparent,
        unfocusedIndicatorColor = TWTheme.colors.transparent,
        disabledIndicatorColor = TWTheme.colors.transparent,
        focusedLeadingIconColor = TWTheme.colors.primary,
        unfocusedLeadingIconColor = TWTheme.colors.primary,
        focusedTrailingIconColor = TWTheme.colors.primary,
        unfocusedTrailingIconColor = TWTheme.colors.primary,
        focusedTextColor = TWTheme.colors.onSurface,
        unfocusedTextColor = TWTheme.colors.onSurface,
        disabledTextColor = TWTheme.colors.onDisabled,
        disabledContainerColor = TWTheme.colors.disabled,
        disabledLeadingIconColor = TWTheme.colors.onDisabled,
        disabledTrailingIconColor = TWTheme.colors.onDisabled
    )
}

@Composable
private fun getOutlinedTextInputColors(): TextFieldColors {
    return OutlinedTextFieldDefaults.colors(
        focusedBorderColor = TWTheme.colors.onSurface,
        unfocusedBorderColor = TWTheme.colors.onSurface,
        focusedContainerColor = TWTheme.colors.surfaceContainer,
        unfocusedContainerColor = TWTheme.colors.surfaceContainer,
        cursorColor = TWTheme.colors.onSurface,
        focusedTrailingIconColor = TWTheme.colors.primary,
        unfocusedTrailingIconColor = TWTheme.colors.primary,
        focusedLeadingIconColor = TWTheme.colors.primary,
        unfocusedLeadingIconColor = TWTheme.colors.primary,
        focusedTextColor = TWTheme.colors.onSurface,
        unfocusedTextColor = TWTheme.colors.onSurface,
        disabledTextColor = TWTheme.colors.onDisabled,
        disabledContainerColor = TWTheme.colors.disabled,
        disabledLeadingIconColor = TWTheme.colors.onDisabled,
        disabledTrailingIconColor = TWTheme.colors.onDisabled
    )
}