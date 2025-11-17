package com.demotest.demo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demotest.demo.data.models.FlowOperation
import com.demotest.demo.data.models.Temperature
import com.demotest.demo.data.repository.FlowRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RealTimeDataViewModel : ViewModel() {
    private val repository = FlowRepository()

    private var dataStreamJob: Job? = null

    private val _dataStream = MutableStateFlow<List<Int>>(emptyList())
    val dataStream: StateFlow<List<Int>> = _dataStream.asStateFlow()

    private val _flowOperations = MutableStateFlow<List<FlowOperation>>(emptyList())
    val flowOperations: StateFlow<List<FlowOperation>> = _flowOperations.asStateFlow()

    private val _isStreaming = MutableStateFlow(false)
    val isStreaming: StateFlow<Boolean> = _isStreaming.asStateFlow()

    fun startDataStream() {
        if (_isStreaming.value) return
        
        _isStreaming.value = true
        _dataStream.value = emptyList()
        
        dataStreamJob = viewModelScope.launch {
            repository.getRealTimeDataStream()
                .take(20)
                .collect { value ->
                    _dataStream.value = (_dataStream.value + value).takeLast(10)
                }
            _isStreaming.value = false
        }
    }

    fun stopDataStream() {
        dataStreamJob?.cancel()
        _isStreaming.value = false
    }

    fun visualizeFlowOperations() {
        viewModelScope.launch {
            _flowOperations.value = emptyList()
            
            repository.getFlowWithOperations()
                .collect { operation ->
                    _flowOperations.value = _flowOperations.value + operation
                }
        }
    }

    fun clearOperations() {
        _flowOperations.value = emptyList()
    }

    override fun onCleared() {
        super.onCleared()
        dataStreamJob?.cancel()
    }
}

