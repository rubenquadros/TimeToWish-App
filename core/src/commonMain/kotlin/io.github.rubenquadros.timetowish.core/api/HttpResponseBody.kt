package io.github.rubenquadros.timetowish.core.api

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

suspend inline fun <reified T>HttpResponse.body(): T {
    return this.body()
}