package io.github.rubenquadros.timetowish.core.base.viewmodel

//import androidx.lifecycle.serialization.saved
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.rubenquadros.timetowish.core.internal.RestartableStateFlow
import io.github.rubenquadros.timetowish.core.internal.restartableStateIn
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
//import kotlinx.serialization.KSerializer

abstract class BaseViewModel<STATE: Any, EVENT: Any>(
    savedStateHandle: SavedStateHandle,
    initialState: STATE,
    //serializer: KSerializer<STATE>
) : ViewModel() {
//    private var _savedState: STATE by savedStateHandle.saved(
//        key = "SAVED_STATE",
//        serializer = serializer
//    ) { initialState }

    private val _uiState: MutableStateFlow<STATE> = MutableStateFlow(initialState/*_savedState*/)
    val uiState: StateFlow<STATE> by lazy {
        merge(_uiState, this.loadInitialData().onEach { _uiState.value = it }).restartableStateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = initialState //_savedState
        )
    }

    private val _uiEvent: Channel<EVENT> = Channel(Channel.BUFFERED)
    val uiEvent: Flow<EVENT> = _uiEvent.receiveAsFlow()

    open fun loadInitialData(): Flow<STATE> = emptyFlow()

    fun updateState(reducer: (STATE) -> STATE) {
        _uiState.update { oldState ->
            reducer(oldState)
           // _savedState = reducer(oldState)
            //_savedState
        }
    }

    fun updateEvent(event: EVENT) {
        viewModelScope.launch { _uiEvent.send(event) }
    }

    fun reloadInitialData() {
        (uiState as? RestartableStateFlow)?.restart()
    }
}