package io.github.rubenquadros.timetowish.core.base

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.serialization.saved
import io.github.rubenquadros.timetowish.core.internal.RestartableStateFlow
import io.github.rubenquadros.timetowish.core.internal.restartableStateIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.KSerializer

/**
 * Contract for maintaining state and event updates.
 * You can use this in tests to provide a stub implementation instead of the real one
 *
 */
interface Container<STATE : Any, EVENT : Any> {
    /**
     * State which is responsible to show data in the UI.
     * The UI elements subscribe to this to render the UI
     */
    val uiState: StateFlow<STATE>

    /**
     * One-off events which are generated in the Viewmodel i.e. our state holder.
     * The UI subscribes to this act on events.
     */
    val uiEvent: Flow<EVENT>

    /**
     * Helper function to update the [uiState] with the new value.
     * This provides access to the previous state so it can be modified or even set a completely different state.
     */
    fun updateState(reducer: (STATE) -> STATE)

    /**
     * Helper function to update the [uiEvent] with a new one-off event.
     */
    fun updateEvent(event: EVENT)

    /**
     * Helper function to re-initialize the UI state.
     * This resets the [uiState] to the initial value and then invokes the function to load the data.
     */
    fun reloadInitialData()
}

/**
 * Use this to handle state/event updates and save your UI state to [SavedStateHandle].
 * In case of system initiated process death, the last updated UI state is automatically restored when the user comes back to the app.
 * Use this class only when the UI state is simple and light.
 * In case the UI state is complex or deeply nested or quite large (example - lot of items in the list)
 * it is recommended to not save it and make user of [StateContainer].
 * This is because storing and restoring involves serialization and deserialization of data on the main thread.
 *
 * @property initialState
 * @property loadInitialData
 * @param scope
 * @param savedStateHandle
 * @param kSerializer
 * @param sharingStarted
 */
class SavedStateContainer<STATE : Any, EVENT : Any>(
    scope: CoroutineScope,
    savedStateHandle: SavedStateHandle,
    kSerializer: KSerializer<STATE>,
    private val initialState: STATE,
    private val loadInitialData: () -> Flow<STATE> = { emptyFlow() },
    sharingStarted: SharingStarted = SharingStarted.Lazily,
) : Container<STATE, EVENT> {

    private var _savedState: STATE by savedStateHandle.saved(
        key = "SAVED_STATE",
        serializer = kSerializer,
        init = { initialState }
    )

    private val _uiState: MutableStateFlow<STATE> = MutableStateFlow(_savedState)
    override val uiState: StateFlow<STATE> = merge(
        _uiState, loadInitialState().conflate().onEach { state ->
            updateState { state }
        }
    ).restartableStateIn(
        scope = scope,
        started = sharingStarted,
        initialValue = initialState
    )

    private val _uiEvent: Channel<EVENT> = Channel(Channel.BUFFERED)
    override val uiEvent: Flow<EVENT> = _uiEvent.receiveAsFlow()

    override fun updateState(reducer: (STATE) -> STATE) {
        _uiState.update { oldState ->
            _savedState = reducer(oldState)
            _savedState
        }
    }

    override fun updateEvent(event: EVENT) {
        _uiEvent.trySend(event)
    }

    override fun reloadInitialData() {
        (uiState as? RestartableStateFlow)?.apply {
            updateState { initialState }
            restart()
        }
    }

    private fun loadInitialState(): Flow<STATE> {
        return if (_uiState.value != initialState) emptyFlow()
        else loadInitialData()
    }
}

/**
 * Use this to handle state/event updates. This does not save the state in the [SavedStateHandle].
 * If you want to save some partial UI state (example - query or unique ID) you can do it in the individual Viewmodel.
 * If you want the functionality to save the entire UI state then refer: [SavedStateContainer].
 *
 * @property initialState
 * @param scope
 * @param loadInitialData
 * @param sharingStarted
 */
class StateContainer<STATE : Any, EVENT : Any>(
    scope: CoroutineScope,
    private val initialState: STATE,
    loadInitialData: () -> Flow<STATE> = { emptyFlow() },
    sharingStarted: SharingStarted = SharingStarted.Lazily,
) : Container<STATE, EVENT> {

    private val _uiState: MutableStateFlow<STATE> = MutableStateFlow(initialState)
    override val uiState: StateFlow<STATE> = merge(
        _uiState, loadInitialData().conflate().onEach { state ->
            updateState { state }
        }
    ).restartableStateIn(
        scope = scope,
        started = sharingStarted,
        initialValue = initialState
    )

    private val _uiEvent: Channel<EVENT> = Channel(Channel.BUFFERED)
    override val uiEvent: Flow<EVENT> = _uiEvent.receiveAsFlow()

    override fun updateState(reducer: (STATE) -> STATE) {
        _uiState.update { oldState ->
            reducer(oldState)
        }
    }

    override fun updateEvent(event: EVENT) {
        _uiEvent.trySend(event)
    }

    override fun reloadInitialData() {
        (uiState as? RestartableStateFlow)?.apply {
            updateState { initialState }
            restart()
        }
    }
}