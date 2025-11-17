package com.demotest.demo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demotest.demo.data.repository.FlowRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)

data class CombinedResult(
    val type: String,
    val values: List<String>
)

class CombinedFlowsViewModel : ViewModel() {
    private val repository = FlowRepository()

    private val _results = MutableStateFlow<List<CombinedResult>>(emptyList())
    val results: StateFlow<List<CombinedResult>> = _results.asStateFlow()

    private val _isProcessing = MutableStateFlow(false)
    val isProcessing: StateFlow<Boolean> = _isProcessing.asStateFlow()

    fun demonstrateCombine() {
        viewModelScope.launch {
            _isProcessing.value = true
            val results = mutableListOf<String>()
            
            combine(
                repository.getFlow1().take(3),
                repository.getFlow2().take(3)
            ) { flow1Value, flow2Value ->
                "$flow1Value + $flow2Value"
            }.collect { combined ->
                results.add(combined)
            }
            
            _results.value = _results.value + 
                CombinedResult("combine", results)
            _isProcessing.value = false
        }
    }

    fun demonstrateZip() {
        viewModelScope.launch {
            _isProcessing.value = true
            val results = mutableListOf<String>()
            
            repository.getFlow1().take(3)
                .zip(repository.getFlow2().take(3)) { flow1Value, flow2Value ->
                    "$flow1Value <-> $flow2Value"
                }
                .collect { zipped ->
                    results.add(zipped)
                }
            
            _results.value = _results.value + 
                CombinedResult("zip", results)
            _isProcessing.value = false
        }
    }

    fun demonstrateMerge() {
        viewModelScope.launch {
            _isProcessing.value = true
            val results = mutableListOf<String>()
            
            merge(
                repository.getFlow1().take(3),
                repository.getFlow2().take(3)
            ).collect { value ->
                results.add(value)
            }
            
            _results.value = _results.value + 
                CombinedResult("merge", results)
            _isProcessing.value = false
        }
    }

    fun demonstrateFlatMapLatest() {
        viewModelScope.launch {
            _isProcessing.value = true
            val results = mutableListOf<String>()
            
            flowOf(1, 2, 3)
                .onEach { kotlinx.coroutines.delay(100) }
                .flatMapLatest { value ->
                    flow {
                        emit("Start $value")
                        kotlinx.coroutines.delay(500)
                        emit("End $value")
                    }
                }
                .collect { result ->
                    results.add(result)
                }
            
            _results.value = _results.value + 
                CombinedResult("flatMapLatest", results)
            _isProcessing.value = false
        }
    }

    fun clearResults() {
        _results.value = emptyList()
    }
}

