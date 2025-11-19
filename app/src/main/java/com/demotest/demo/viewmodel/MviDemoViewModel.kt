package com.demotest.demo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * MVI Demo ViewModel
 * 
 * MVI Pattern components:
 * 1. Model: UiState (represents current UI state)
 * 2. View: Composable (renders state and sends intents)
 * 3. Intent: UserIntent (user actions/intentions)
 * 
 * Flow: View → Intent → ViewModel → Model → View
 */
class MviDemoViewModel : ViewModel() {

    // Intent: All possible user actions
    sealed class UserIntent {
        data class LoadUser(val userId: Int) : UserIntent()
        data object RefreshData : UserIntent()
        data class UpdateText(val text: String) : UserIntent()
    }

    // Model: UI State - Single source of truth
    data class UiState(
        val userName: String = "Not loaded",
        val userEmail: String = "",
        val isLoading: Boolean = false,
        val error: String? = null,
        val lastAction: String = "None",
        val textInput: String = ""
    )

    // State holder
    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    /**
     * Main entry point for all user intents
     * All user actions flow through this function
     */
    fun processIntent(intent: UserIntent) {
        when (intent) {
            is UserIntent.LoadUser -> loadUser(intent.userId)
            is UserIntent.RefreshData -> refreshData()
            is UserIntent.UpdateText -> updateText(intent.text)
        }
    }

    private fun loadUser(userId: Int) {
        viewModelScope.launch {
            // Set loading state
            _state.value = _state.value.copy(
                isLoading = true,
                error = null,
                lastAction = "Loading user $userId..."
            )

            try {
                // Simulate network call
                delay(1500)
                
                // Simulate successful response
                val users = mapOf(
                    1 to Pair("Alice Johnson", "alice@example.com"),
                    2 to Pair("Bob Smith", "bob@example.com"),
                    3 to Pair("Charlie Brown", "charlie@example.com")
                )
                
                val user = users[userId] ?: Pair("Unknown User", "unknown@example.com")
                
                _state.value = _state.value.copy(
                    userName = user.first,
                    userEmail = user.second,
                    isLoading = false,
                    lastAction = "Loaded user successfully"
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Failed to load user",
                    lastAction = "Error loading user"
                )
            }
        }
    }

    private fun refreshData() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                lastAction = "Refreshing data..."
            )

            delay(1000)

            _state.value = _state.value.copy(
                isLoading = false,
                lastAction = "Data refreshed at ${System.currentTimeMillis() % 10000}"
            )
        }
    }

    private fun updateText(text: String) {
        _state.value = _state.value.copy(
            textInput = text,
            lastAction = "Updated text"
        )
    }
}

