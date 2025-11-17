package com.demotest.demo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*

data class CounterState(
    val count: Int = 0,
    val isEven: Boolean = true
)

class StateFlowViewModel : ViewModel() {
    
    private val _counterState = MutableStateFlow(CounterState())
    val counterState: StateFlow<CounterState> = _counterState.asStateFlow()

    private val _firstName = MutableStateFlow("")
    val firstName: StateFlow<String> = _firstName.asStateFlow()

    private val _lastName = MutableStateFlow("")
    val lastName: StateFlow<String> = _lastName.asStateFlow()

    val fullName: StateFlow<String> = combine(_firstName, _lastName) { first, last ->
        if (first.isNotEmpty() && last.isNotEmpty()) {
            "$first $last"
        } else {
            ""
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ""
    )

    fun incrementCounter() {
        val newCount = _counterState.value.count + 1
        _counterState.value = CounterState(
            count = newCount,
            isEven = newCount % 2 == 0
        )
    }

    fun decrementCounter() {
        val newCount = _counterState.value.count - 1
        _counterState.value = CounterState(
            count = newCount,
            isEven = newCount % 2 == 0
        )
    }

    fun resetCounter() {
        _counterState.value = CounterState()
    }

    fun updateFirstName(name: String) {
        _firstName.value = name
    }

    fun updateLastName(name: String) {
        _lastName.value = name
    }
}

