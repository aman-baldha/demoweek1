package com.demotest.demo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demotest.demo.data.repository.FlowRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class UiEvent {
    data class ShowToast(val message: String) : UiEvent()
    data class NavigateTo(val route: String) : UiEvent()
    object ShowLoading : UiEvent()
    object HideLoading : UiEvent()
}

class SharedFlowViewModel : ViewModel() {
    private val repository = FlowRepository()

    // SharedFlow for one-time events
    private val _uiEvents = MutableSharedFlow<UiEvent>()
    val uiEvents: SharedFlow<UiEvent> = _uiEvents.asSharedFlow()

    // SharedFlow for broadcasting messages
    private val _messages = MutableSharedFlow<String>(replay = 5)
    val messages: SharedFlow<String> = _messages.asSharedFlow()

    // Collect from repository's hot flow
    val hotFlowData: SharedFlow<String> = repository.hotFlow

    private val _collectedMessages = MutableStateFlow<List<String>>(emptyList())
    val collectedMessages: StateFlow<List<String>> = _collectedMessages.asStateFlow()

    init {
        // Collect messages for display
        viewModelScope.launch {
            messages.collect { message ->
                _collectedMessages.value = _collectedMessages.value + message
            }
        }

        // Collect hot flow data
        viewModelScope.launch {
            hotFlowData.collect { data ->
                _collectedMessages.value = _collectedMessages.value + "Hot: $data"
            }
        }
    }

    fun sendEvent(message: String) {
        viewModelScope.launch {
            _uiEvents.emit(UiEvent.ShowToast(message))
        }
    }

    fun sendLoadingEvent() {
        viewModelScope.launch {
            _uiEvents.emit(UiEvent.ShowLoading)
        }
    }

    fun sendHideLoadingEvent() {
        viewModelScope.launch {
            _uiEvents.emit(UiEvent.HideLoading)
        }
    }

    fun broadcastMessage(message: String) {
        viewModelScope.launch {
            _messages.emit(message)
        }
    }

    fun emitToHotFlow(value: String) {
        viewModelScope.launch {
            repository.emitToHotFlow(value)
        }
    }

    fun clearMessages() {
        _collectedMessages.value = emptyList()
    }
}

