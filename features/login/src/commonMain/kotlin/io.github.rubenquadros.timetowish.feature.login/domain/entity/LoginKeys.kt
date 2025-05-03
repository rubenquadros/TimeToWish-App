package io.github.rubenquadros.timetowish.feature.login.domain.entity

internal data class LoginData(
    val keys: LoginKeys,
    val pages: LoginPages
)

internal data class LoginKeys(
    val google: PlatformKey
)

internal data class PlatformKey(
    val android: String,
    val ios: String,
    val server: String
)

internal data class LoginPages(
    val termsAndConditions: String,
    val privacyPolicy: String
)
