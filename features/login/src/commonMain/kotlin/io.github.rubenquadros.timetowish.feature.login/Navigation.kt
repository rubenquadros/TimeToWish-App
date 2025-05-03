package io.github.rubenquadros.timetowish.feature.login

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material.navigation.bottomSheet
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import io.github.rubenquadros.timetowish.feature.login.presentation.ui.LoginScreen
import io.github.rubenquadros.timetowish.feature.login.presentation.ui.TermsAndPolicyBottomSheet
import io.github.rubenquadros.timetowish.feature.login.presentation.ui.UrlType
import io.github.rubenquadros.timetowish.navigation.routes.NavigateTo
import io.github.rubenquadros.timetowish.navigation.routes.login.LoginRoute.LoginScreen
import io.github.rubenquadros.timetowish.navigation.routes.login.LoginRoute.TermsAndPrivacy
import io.github.rubenquadros.timetowish.shared.presentation.webPage.displayContent

fun NavGraphBuilder.loginScreen(
    navigateUp: () -> Unit,
    navigateTo: NavigateTo
) {
    composable<LoginScreen>(
        enterTransition = { fadeIn(tween(500), initialAlpha = 0.3f) },
        exitTransition = { fadeOut(tween(300)) }
    ) {
        LoginScreen(navigateUp = navigateUp, navigateTo = navigateTo)
    }

    bottomSheet<TermsAndPrivacy> { navBackStackEntry ->
        val termsAndPrivacy = navBackStackEntry.toRoute<TermsAndPrivacy>()
        TermsAndPolicyBottomSheet { type ->
            displayContent(
                url = if (type == UrlType.TERMS) {
                    termsAndPrivacy.terms
                } else {
                    termsAndPrivacy.privacy
                }
            )
        }
    }
}