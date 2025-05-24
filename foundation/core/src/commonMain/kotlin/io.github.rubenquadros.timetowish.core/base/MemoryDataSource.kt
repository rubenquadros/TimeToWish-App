package io.github.rubenquadros.timetowish.core.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * This class data in-memory. Use it if you want to save some data only for the
 * duration of the lifecycle of the app process.
 */
abstract class MemoryDataSource<T> {

    private val data: MutableStateFlow<T?> = MutableStateFlow(null)

    /**
     * Saves the non-null data
     * @param data data to be saved
     */
    suspend fun save(data: T) {
        this.data.emit(data)
    }

    /**
     * @return the flow of data
     */
    fun load(): Flow<T?> = data

    /**
     * @return the last stored value if any, otherwise null
     */
    fun latest(): T? = data.value

    /**
     * Clears the stored data in the state flow by emitting a null value
     */
    suspend fun clear() {
        data.emit(null)
    }
}