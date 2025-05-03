package io.github.rubenquadros.timetowish.feature.login.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetLoginDataResponse(
    val keys: GetLoginKeysResponse,
    val pages: GetLoginPagesResponse
)

@Serializable
data class GetLoginKeysResponse(
    val google: GetPlatformKeyResponse
)

@Serializable
data class GetPlatformKeyResponse(
    val android: String,
    val ios: String,
    val server: String
)

@Serializable
data class GetLoginPagesResponse(
    @SerialName("termsLink")
    val termsAndConditions: String,
    @SerialName("privacyLink")
    val privacyPolicy: String
)
