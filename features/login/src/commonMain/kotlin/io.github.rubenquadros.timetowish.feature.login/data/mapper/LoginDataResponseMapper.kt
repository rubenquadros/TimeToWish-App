package io.github.rubenquadros.timetowish.feature.login.data.mapper

import io.github.rubenquadros.timetowish.feature.login.data.model.GetLoginDataResponse
import io.github.rubenquadros.timetowish.feature.login.data.model.GetLoginKeysResponse
import io.github.rubenquadros.timetowish.feature.login.data.model.GetLoginPagesResponse
import io.github.rubenquadros.timetowish.feature.login.data.model.GetPlatformKeyResponse
import io.github.rubenquadros.timetowish.feature.login.domain.entity.LoginData
import io.github.rubenquadros.timetowish.feature.login.domain.entity.LoginKeys
import io.github.rubenquadros.timetowish.feature.login.domain.entity.LoginPages
import io.github.rubenquadros.timetowish.feature.login.domain.entity.PlatformKey

internal fun GetLoginDataResponse.toLoginData(): LoginData {
    return LoginData(
        keys = keys.toLoginKeys(),
        pages = pages.toLoginPages()
    )
}

private fun GetLoginKeysResponse.toLoginKeys(): LoginKeys {
    return LoginKeys(
        google = google.toPlatformKeys()
    )
}

private fun GetPlatformKeyResponse.toPlatformKeys(): PlatformKey {
    return PlatformKey(
        android = android,
        ios = ios,
        server = server
    )
}

private fun GetLoginPagesResponse.toLoginPages(): LoginPages {
    return LoginPages(
        termsAndConditions = termsAndConditions,
        privacyPolicy = privacyPolicy
    )
}