package com.demotest.demo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demotest.demo.data.models.FlowItem
import com.demotest.demo.data.repository.FlowRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class BasicFlowViewModel : ViewModel() {
    private val repository = FlowRepository()

    private val _flowItems = MutableStateFlow<List<FlowItem>>(emptyList())
    val flowItems: StateFlow<List<FlowItem>> = _flowItems.asStateFlow()

    private val _isCollecting = MutableStateFlow(false)
    val isCollecting: StateFlow<Boolean> = _isCollecting.asStateFlow()

    private val _coldFlowResults = MutableStateFlow<List<String>>(emptyList())
    val coldFlowResults: StateFlow<List<String>> = _coldFlowResults.asStateFlow()

    fun startBasicFlow() {
        viewModelScope.launch {
            _isCollecting.value = true
            _flowItems.value = emptyList()
            
            repository.getBasicFlow()
                .onEach { item -> _flowItems.value = _flowItems.value + item }
                .onCompletion { _isCollecting.value = false }
                .catch { _isCollecting.value = false }
                .collect()
        }
    }

    fun demonstrateColdFlow() {
        viewModelScope.launch {
            _coldFlowResults.value = emptyList()
            
            launch {
                repository.getColdFlow().collect { value ->
                    _coldFlowResults.value = _coldFlowResults.value + "Collector 1: $value"
                }
            }

            launch {
                kotlinx.coroutines.delay(1000)
                repository.getColdFlow().collect { value ->
                    _coldFlowResults.value = _coldFlowResults.value + "Collector 2: $value"
                }
            }
        }
    }

    fun clearResults() {
        _flowItems.value = emptyList()
        _coldFlowResults.value = emptyList()
    }
}

