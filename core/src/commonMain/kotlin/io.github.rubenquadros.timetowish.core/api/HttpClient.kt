package io.github.rubenquadros.timetowish.core.api

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.statement.HttpResponseContainer
import io.ktor.client.statement.HttpResponsePipeline
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single

interface ApiClient {
    val client: HttpClient
}

@Single
internal class ApiClientImpl : ApiClient {
    override val client: HttpClient
        get() = getHttpClient()

    private fun getHttpClient(): HttpClient {
        return HttpClient(
            engine = getHttpClientEngine()
        ){
            install(ContentNegotiation) {
                json(Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    encodeDefaults = true
                    explicitNulls = false
                })
            }

            install(Logging) {
                level = LogLevel.ALL
            }
        }.also {
            it.addResponseInterceptor()
        }
    }

    private fun HttpClient.addResponseInterceptor() {
        responsePipeline.intercept(phase = HttpResponsePipeline.Transform) { (info, body) ->
            if (context.response.status != HttpStatusCode.OK) {
                //parse error response and throw exception
                val response = body as ApiError
                throw ApiException(message = response.message)
            }

            proceedWith(HttpResponseContainer(info, body))
        }
    }
}