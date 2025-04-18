package io.github.rubenquadros.timetowish.shared.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.rubenquadros.timetowish.ui.TWTheme
import io.github.rubenquadros.timetowish.ui.button.TWButton
import io.github.rubenquadros.timetowish.ui.image.ImageReference
import io.github.rubenquadros.timetowish.ui.image.TWImage
import io.github.rubenquadros.timetowish.ui.text.TWText
import org.jetbrains.compose.resources.stringResource
import timetowish.shared.generated.resources.Res
import timetowish.shared.generated.resources.common_contact_support
import timetowish.shared.generated.resources.common_error
import timetowish.shared.generated.resources.common_error_image_accessibility_label
import timetowish.shared.generated.resources.common_error_message

interface ErrorScreen {
    
    data class Cta(
        val text: String,
        val onClick: () -> Unit
    )
}

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    message: String = stringResource(Res.string.common_error_message),
    cta: ErrorScreen.Cta? = null,
    contactSupport: Boolean = true
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(TWTheme.spacings.space2)
    ) { 
        //image
        TWImage(
            modifier = Modifier.size(ERROR_IMAGE_SIZE),
            imageReference = ImageReference.ResImage(Res.drawable.common_error),
            accessibilityLabel = stringResource(Res.string.common_error_image_accessibility_label)
        )

        //description
        TWText(
            modifier = Modifier.padding(horizontal = TWTheme.spacings.space8),
            text = message,
            textStyle = TWTheme.typography.bodyMedium
        )


        //primary cta
        cta?.let { button ->
            TWButton(
                modifier = Modifier.padding(horizontal = TWTheme.spacings.space8).fillMaxWidth(),
                content = TWButton.Content.Text(
                    text = button.text
                ),
                onClick = button.onClick
            )
        }

        //secondary support cta
        if (contactSupport) {
            TWButton(
                modifier = Modifier.padding(horizontal = TWTheme.spacings.space8).fillMaxWidth(),
                variant = TWButton.Variant.Secondary,
                content = TWButton.Content.Text(
                    text = stringResource(Res.string.common_contact_support)
                ),
                onClick = {
                    //contact support
                }
            )
        }
    }
}

private val ERROR_IMAGE_SIZE = 120.dp