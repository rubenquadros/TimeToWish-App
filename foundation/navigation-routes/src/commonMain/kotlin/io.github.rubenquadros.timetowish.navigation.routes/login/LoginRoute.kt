package io.github.rubenquadros.timetowish.navigation.routes.login

import io.github.rubenquadros.timetowish.navigation.routes.TWDestination
import kotlinx.serialization.Serializable

sealed interface LoginRoute : TWDestination {
    @Serializable
    data object LoginScreen : LoginRoute

    @Serializable
    data class TermsAndPrivacy(
        val terms: String,
        val privacy: String
    ) : LoginRoute
}