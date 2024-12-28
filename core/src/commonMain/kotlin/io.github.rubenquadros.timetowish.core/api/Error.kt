package io.github.rubenquadros.timetowish.core.api

import kotlinx.serialization.Serializable

class ApiException(override val message: String): Exception(message)

@Serializable
data class ApiError(val message: String)