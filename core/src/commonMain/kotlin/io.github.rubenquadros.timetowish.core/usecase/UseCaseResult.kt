package io.github.rubenquadros.timetowish.core.usecase

sealed interface UseCaseResult<out DATA> {
    data class Success<DATA>(val data: DATA): UseCaseResult<DATA>
    data object NetworkError: UseCaseResult<Nothing>
    data class Error(val message: String): UseCaseResult<Nothing>
    data object Unknown: UseCaseResult<Nothing>
}