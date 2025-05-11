package io.github.rubenquadros.timetowish.feature.login.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.FixedScale
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import io.github.rubenquadros.timetowish.feature.login.presentation.LoginState
import io.github.rubenquadros.timetowish.feature.login.resources.Res
import io.github.rubenquadros.timetowish.feature.login.resources.app_logo
import io.github.rubenquadros.timetowish.feature.login.resources.login_app_icon_accessibility_label
import io.github.rubenquadros.timetowish.feature.login.resources.login_copyright
import io.github.rubenquadros.timetowish.feature.login.resources.login_description
import io.github.rubenquadros.timetowish.feature.login.resources.login_error_info_accessibility_label
import io.github.rubenquadros.timetowish.feature.login.resources.login_info
import io.github.rubenquadros.timetowish.feature.login.resources.login_policies_view_details_cta
import io.github.rubenquadros.timetowish.feature.login.resources.login_title
import io.github.rubenquadros.timetowish.feature.login.resources.login_tnc
import io.github.rubenquadros.timetowish.feature.login.resources.login_validation_error
import io.github.rubenquadros.timetowish.shared.presentation.ui.Loader
import io.github.rubenquadros.timetowish.shared.presentation.ui.TWTopAppBar
import io.github.rubenquadros.timetowish.shared.presentation.ui.backIcon
import io.github.rubenquadros.timetowish.shared.presentation.ui.enterFadeInTransition
import io.github.rubenquadros.timetowish.shared.presentation.ui.existFadeOutTransition
import io.github.rubenquadros.timetowish.ui.TWTheme
import io.github.rubenquadros.timetowish.ui.banner.TWBanner
import io.github.rubenquadros.timetowish.ui.button.TWButton
import io.github.rubenquadros.timetowish.ui.image.ImageReference
import io.github.rubenquadros.timetowish.ui.image.TWImage
import io.github.rubenquadros.timetowish.ui.text.TWText
import io.github.rubenquadros.timetowish.ui.text.TWTitle
import org.jetbrains.compose.resources.stringResource

/**
 * @see [io.github.rubenquadros.timetowish.feature.login.ui.preview.LoginContentPreview]
 */
@Composable
internal fun LoginContent(
    loginState: LoginState,
    navigateUp: () -> Unit,
    onLoginClick: () -> Unit,
    onViewDetailsClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LoginContentInternal(
            loginState = loginState,
            onLoginClick = onLoginClick,
            onViewDetailsClick = onViewDetailsClick,
            navigateUp = navigateUp
        )

        //loading
        LoginLoading(shouldShow = loginState.showLoginLoading)
    }
}

@Composable
private fun LoginContentInternal(
    loginState: LoginState,
    navigateUp: () -> Unit,
    onLoginClick: () -> Unit,
    onViewDetailsClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.TopCenter),
        ) {
            TWTopAppBar(
                icon = backIcon { navigateUp() },
            )

            //show login error
            LoginError(shouldShow = loginState.showLoginError)

            //app_logo
            TWImage(
                modifier = Modifier
                    .size(TWTheme.spacings.space24)
                    .align(Alignment.CenterHorizontally)
                    .clearAndSetSemantics {  }, //do not read out in talkback
                imageReference = ImageReference.ResImage(Res.drawable.app_logo),
                contentScale = FixedScale(1f),
                accessibilityLabel = stringResource(Res.string.login_app_icon_accessibility_label)
            )

            Spacer(modifier = Modifier.fillMaxWidth().height(TWTheme.spacings.space4))

            //login form
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(TWTheme.spacings.space4),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TWTitle(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = TWTheme.spacings.space4),
                    title = stringResource(Res.string.login_title),
                    textStyle = TWTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                TWText(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = TWTheme.spacings.space4),
                    text = stringResource(Res.string.login_description),
                    textStyle = TWTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.fillMaxWidth().height(TWTheme.spacings.space4))

                TWButton(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = TWTheme.spacings.space4),
                    variant = TWButton.Variant.Primary,
                    content = TWButton.Content.Text(
                        text = stringResource(loginState.getLoginButtonText()),
                        icon = TWButton.TextIcon(
                            imageReference = ImageReference.ResImage(loginState.getLoginButtonImage()),
                            accessibilityLabel = "", //We do not announce the image in talk back
                            position = TWButton.TextIconPosition.START
                        )
                    ),
                    onClick = onLoginClick
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TWText(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = TWTheme.spacings.space4),
                        text = stringResource(Res.string.login_tnc),
                        textStyle = TWTheme.typography.titleSmall,
                        textAlign = TextAlign.Center
                    )

                    TWButton(
                        content = TWButton.Content.Text(
                            text = stringResource(Res.string.login_policies_view_details_cta)
                        ),
                        variant = TWButton.Variant.Tertiary,
                        onClick = onViewDetailsClick
                    )
                }
            }
        }

        TWText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(TWTheme.spacings.space4)
                .align(Alignment.BottomCenter),
            text = stringResource(Res.string.login_copyright),
            textStyle = TWTheme.typography.titleSmall,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun LoginLoading(shouldShow: Boolean) {
    AnimatedVisibility(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = { /* Simply consume clicks and do nothing */ }
            ),
        visible = shouldShow,
        enter = enterFadeInTransition,
        exit = existFadeOutTransition
    ) {
        Loader(modifier = Modifier.fillMaxSize())
    }
}

@Composable
private fun LoginError(shouldShow: Boolean) {
    AnimatedVisibility(
        visible = shouldShow,
        enter = fadeIn() + slideInVertically(tween(380)),
        exit = fadeOut() + slideOutVertically(tween(340))
    ) {
        Column {
            TWBanner(
                modifier = Modifier.fillMaxWidth().padding(horizontal = TWTheme.spacings.space4),
                variant = TWBanner.Variant.Error,
                content = TWBanner.TextContent(
                    text = stringResource(Res.string.login_validation_error),
                    textStyle = TWTheme.typography.bodyMedium
                ),
                icon = TWBanner.Icon(
                    imageReference = ImageReference.ResImage(Res.drawable.login_info),
                    accessibilityLabel = stringResource(Res.string.login_error_info_accessibility_label)
                )
            )

            Spacer(modifier = Modifier.fillMaxWidth().height(TWTheme.spacings.space4))
        }
    }
}