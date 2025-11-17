package com.demotest.demo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demotest.demo.data.repository.FlowRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class OperatorResult(
    val operator: String,
    val values: List<String>
)

class FlowOperatorsViewModel : ViewModel() {
    private val repository = FlowRepository()

    private val _operatorResults = MutableStateFlow<List<OperatorResult>>(emptyList())
    val operatorResults: StateFlow<List<OperatorResult>> = _operatorResults.asStateFlow()

    private val _isProcessing = MutableStateFlow(false)
    val isProcessing: StateFlow<Boolean> = _isProcessing.asStateFlow()

    fun demonstrateMapOperator() {
        viewModelScope.launch {
            _isProcessing.value = true
            val results = mutableListOf<String>()
            
            repository.getNumbersFlow()
                .take(5)
                .map { it * 2 }
                .collect { value ->
                    results.add("$value")
                }
            
            _operatorResults.value = _operatorResults.value + 
                OperatorResult("map (x2)", results)
            _isProcessing.value = false
        }
    }

    fun demonstrateFilterOperator() {
        viewModelScope.launch {
            _isProcessing.value = true
            val results = mutableListOf<String>()
            
            repository.getNumbersFlow()
                .take(10)
                .filter { it % 2 == 0 }
                .collect { value ->
                    results.add("$value (even)")
                }
            
            _operatorResults.value = _operatorResults.value + 
                OperatorResult("filter (even)", results)
            _isProcessing.value = false
        }
    }

    fun demonstrateTransformOperator() {
        viewModelScope.launch {
            _isProcessing.value = true
            val results = mutableListOf<String>()
            
            repository.getNumbersFlow()
                .take(3)
                .transform { value ->
                    emit("Before: $value")
                    emit("After: ${value * 10}")
                }
                .collect { value ->
                    results.add(value)
                }
            
            _operatorResults.value = _operatorResults.value + 
                OperatorResult("transform", results)
            _isProcessing.value = false
        }
    }

    fun demonstrateTakeOperator() {
        viewModelScope.launch {
            _isProcessing.value = true
            val results = mutableListOf<String>()
            
            repository.getNumbersFlow()
                .take(5)
                .collect { value ->
                    results.add("$value")
                }
            
            _operatorResults.value = _operatorResults.value + 
                OperatorResult("take(5)", results)
            _isProcessing.value = false
        }
    }

    fun demonstrateDropOperator() {
        viewModelScope.launch {
            _isProcessing.value = true
            val results = mutableListOf<String>()
            
            repository.getNumbersFlow()
                .take(10)
                .drop(5)
                .collect { value ->
                    results.add("$value")
                }
            
            _operatorResults.value = _operatorResults.value + 
                OperatorResult("drop(5)", results)
            _isProcessing.value = false
        }
    }

    fun demonstrateReduceOperator() {
        viewModelScope.launch {
            _isProcessing.value = true
            
            val sum = repository.getNumbersFlow()
                .take(10)
                .reduce { accumulator, value -> accumulator + value }
            
            _operatorResults.value = _operatorResults.value + 
                OperatorResult("reduce (sum)", listOf("Sum: $sum"))
            _isProcessing.value = false
        }
    }

    fun clearResults() {
        _operatorResults.value = emptyList()
    }
}

