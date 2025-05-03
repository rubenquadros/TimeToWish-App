package io.github.rubenquadros.timetowish.feature.login.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.rubenquadros.timetowish.feature.login.resources.Res
import io.github.rubenquadros.timetowish.feature.login.resources.login_privacy
import io.github.rubenquadros.timetowish.feature.login.resources.login_privacy_accessibility_label
import io.github.rubenquadros.timetowish.feature.login.resources.login_terms
import io.github.rubenquadros.timetowish.feature.login.resources.login_terms_accessibility_label
import io.github.rubenquadros.timetowish.feature.login.resources.login_terms_subtitle
import io.github.rubenquadros.timetowish.feature.login.resources.login_terms_title
import io.github.rubenquadros.timetowish.ui.TWTheme
import io.github.rubenquadros.timetowish.ui.button.TWButton
import io.github.rubenquadros.timetowish.ui.divider.TWDivider
import io.github.rubenquadros.timetowish.ui.image.ImageReference
import io.github.rubenquadros.timetowish.ui.text.TWText
import io.github.rubenquadros.timetowish.ui.text.TWTitle
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun TermsAndPolicyBottomSheet(
    onClick: (type: UrlType) -> Unit
) {
    Column(
        modifier = Modifier.navigationBarsPadding().fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(TWTheme.spacings.space2)
    ) {
        Spacer(modifier = Modifier.height(TWTheme.spacings.space4))

        //title and subtitle
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(TWTheme.spacings.space1)
        ) {
            TWTitle(
                modifier = Modifier.padding(horizontal = TWTheme.spacings.space4),
                title = stringResource(Res.string.login_terms_title)
            )

            TWText(
                modifier = Modifier.padding(horizontal = TWTheme.spacings.space4),
                text = stringResource(Res.string.login_terms_subtitle),
                textStyle = TWTheme.typography.bodyMedium
            )
        }

        TWDivider(
            modifier = Modifier.padding(horizontal = TWTheme.spacings.space2),
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            TWButton(
                content = TWButton.Content.Text(
                    text = stringResource(Res.string.login_terms),
                    icon = TWButton.TextIcon(
                        imageReference = ImageReference.ResImage(Res.drawable.login_terms),
                        accessibilityLabel = stringResource(Res.string.login_terms_accessibility_label),
                        position = TWButton.TextIconPosition.START
                    )
                ),
                variant = TWButton.Variant.Tertiary,
                onClick = { onClick(UrlType.TERMS) }
            )

            TWButton(
                content = TWButton.Content.Text(
                    text = stringResource(Res.string.login_privacy),
                    icon = TWButton.TextIcon(
                        imageReference = ImageReference.ResImage(Res.drawable.login_privacy),
                        accessibilityLabel = stringResource(Res.string.login_privacy_accessibility_label),
                        position = TWButton.TextIconPosition.START
                    )
                ),
                variant = TWButton.Variant.Tertiary,
                onClick = { onClick(UrlType.PRIVACY) }
            )
        }
    }
}

internal enum class UrlType {
    TERMS, PRIVACY;
}