package io.github.rubenquadros.timetowish.core.base

import io.github.rubenquadros.timetowish.core.api.ApiException
import io.ktor.client.network.sockets.SocketTimeoutException
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Extend your UseCase classes with [UseCase] if you do not want to wrap your result with [UseCaseResult]
 */
abstract class UseCase<in REQUEST, out RESPONSE> {

    operator fun invoke(request: REQUEST): RESPONSE? {
        return runCatching {
            execute(request)
        }.getOrNull()
    }

    abstract fun execute(request: REQUEST): RESPONSE
}

/**
 * Extend your UseCase classes with [BaseUseCase] for a single shot response
 * @see UseCaseResult
 */
abstract class BaseUseCase<in REQUEST, out RESPONSE>(
    open val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(request: REQUEST): UseCaseResult<RESPONSE> {
        return try {
            withContext(dispatcher) {
                UseCaseResult.Success(execute(request))
            }
        } catch (e: Exception) {
            getUseCaseResultFromThrowable(e)
        }
    }

    abstract suspend fun execute(request: REQUEST): RESPONSE
}

/**
 * Extend your UseCase with [BaseFlowUseCase] if you want to wrap the response in a [Flow]
 * @see UseCaseResult
 */
abstract class BaseFlowUseCase<in REQUEST, out RESPONSE>(
    open val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    operator fun invoke(request: REQUEST): Flow<UseCaseResult<RESPONSE>> {
        return flow {
            val result = try {
                UseCaseResult.Success(execute(request))
            } catch (e: Exception) {
                getUseCaseResultFromThrowable(e)
            }

            emit(result)
        }.flowOn(dispatcher)
    }

    abstract suspend fun execute(request: REQUEST): RESPONSE
}

private fun <DATA>getUseCaseResultFromThrowable(exception: Throwable): UseCaseResult<DATA> {
    return when (exception) {
        is CancellationException -> throw exception
        is SocketTimeoutException -> UseCaseResult.NetworkError
        is ApiException -> UseCaseResult.Error(message = exception.message)
        else -> UseCaseResult.Unknown
    }
}

sealed interface UseCaseResult<out DATA> {
    data class Success<DATA>(val data: DATA): UseCaseResult<DATA>
    data object NetworkError: UseCaseResult<Nothing>
    data class Error(val message: String): UseCaseResult<Nothing>
    data object Unknown: UseCaseResult<Nothing>
}