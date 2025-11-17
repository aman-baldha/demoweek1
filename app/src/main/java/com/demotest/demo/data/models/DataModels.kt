package com.demotest.demo.data.models

data class FlowItem(
    val id: Int,
    val value: String,
    val timestamp: Long = System.currentTimeMillis()
)

data class User(
    val id: Int,
    val name: String,
    val email: String
)

data class Temperature(
    val value: Double,
    val unit: String = "°C",
    val location: String
)

data class SearchResult(
    val query: String,
    val results: List<String>,
    val timestamp: Long = System.currentTimeMillis()
)

sealed class NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error(val message: String) : NetworkResult<Nothing>()
    object Loading : NetworkResult<Nothing>()
}

data class FlowOperation(
    val stage: String,
    val value: String,
    val timestamp: Long = System.currentTimeMillis()
)

enum class OperationStage {
    LOADING,
    EMITTING,
    TRANSFORMING,
    COLLECTING,
    COMPLETED
}

