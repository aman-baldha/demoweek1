package com.demotest.demo.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Simple MVVM Demo ViewModel
 * Demonstrates the key concepts of MVVM architecture:
 * - ViewModel holds UI state
 * - State is exposed as immutable Flow
 * - UI events trigger state updates
 */
class MvvmDemoViewModel : ViewModel() {

    // State class - represents the UI state
    data class UiState(
        val counter: Int = 0,
        val userName: String = "Guest",
        val isLoading: Boolean = false
    )

    // Private mutable state (only ViewModel can modify)
    private val _uiState = MutableStateFlow(UiState())
    
    // Public immutable state (UI can only observe)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    // UI Events - these are actions triggered by the user
    
    fun incrementCounter() {
        _uiState.update { currentState ->
            currentState.copy(counter = currentState.counter + 1)
        }
    }
    
    fun decrementCounter() {
        _uiState.update { currentState ->
            currentState.copy(counter = currentState.counter - 1)
        }
    }
    
    fun resetCounter() {
        _uiState.update { currentState ->
            currentState.copy(counter = 0)
        }
    }
    
    fun updateUserName(name: String) {
        _uiState.update { currentState ->
            currentState.copy(userName = name.ifBlank { "Guest" })
        }
    }
    
    fun toggleLoading() {
        _uiState.update { currentState ->
            currentState.copy(isLoading = !currentState.isLoading)
        }
    }
}

