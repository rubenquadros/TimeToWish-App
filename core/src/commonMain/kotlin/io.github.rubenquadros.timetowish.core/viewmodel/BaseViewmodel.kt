package io.github.rubenquadros.timetowish.core.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.serialization.saved
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.serialization.KSerializer

abstract class BaseViewModel<STATE: Any>(
    savedStateHandle: SavedStateHandle,
    initialState: STATE,
    serializer: KSerializer<STATE>
) : ViewModel() {
    private var _savedState: STATE by savedStateHandle.saved(
        key = "SAVED_STATE",
        serializer = serializer
    ) { initialState }

    private val _uiState: MutableStateFlow<STATE> = MutableStateFlow(_savedState)
    val uiState: StateFlow<STATE> = merge(_uiState, this.loadInitialData()).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = _savedState
    )

    open fun loadInitialData(): Flow<STATE> = flow {  }

    fun updateState(reducer: (STATE) -> STATE) {
        _uiState.update { oldState ->
            _savedState = reducer(oldState)
            _savedState
        }
    }
}