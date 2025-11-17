package com.demotest.demo.data.repository

import com.demotest.demo.data.models.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlin.random.Random

class FlowRepository {

    fun getBasicFlow(): Flow<FlowItem> = flow {
        repeat(10) { index ->
            delay(1000)
            emit(FlowItem(id = index, value = "Item $index"))
        }
    }

    fun getColdFlow(): Flow<Int> = flow {
        repeat(5) { index ->
            delay(500)
            emit(index)
        }
    }

    private val _hotFlow = MutableSharedFlow<String>(replay = 1)
    val hotFlow: SharedFlow<String> = _hotFlow.asSharedFlow()

    suspend fun emitToHotFlow(value: String) {
        _hotFlow.emit(value)
    }

    private val _userState = MutableStateFlow<NetworkResult<User>>(NetworkResult.Loading)
    val userState: StateFlow<NetworkResult<User>> = _userState.asStateFlow()

    suspend fun loadUser(userId: Int) {
        _userState.value = NetworkResult.Loading
        delay(1500)
        
        if (Random.nextBoolean()) {
            _userState.value = NetworkResult.Success(
                User(userId, "User $userId", "user$userId@example.com")
            )
        } else {
            _userState.value = NetworkResult.Error("Failed to load user")
        }
    }

    fun getNumbersFlow(): Flow<Int> = flow {
        (1..20).forEach { number ->
            delay(300)
            emit(number)
        }
    }

    fun getTemperatureFlow(location: String): Flow<Temperature> = flow {
        while (true) {
            val temp = Random.nextDouble(15.0, 35.0)
            emit(Temperature(value = temp, location = location))
            delay(2000)
        }
    }

    fun searchFlow(query: String): Flow<SearchResult> = flow {
        delay(500)
        val results = listOf(
            "$query - Result 1",
            "$query - Result 2",
            "$query - Result 3"
        ).filter { it.contains(query, ignoreCase = true) }
        emit(SearchResult(query = query, results = results))
    }

    fun getFlow1(): Flow<String> = flow {
        repeat(5) { index ->
            delay(1000)
            emit("Flow1: $index")
        }
    }

    fun getFlow2(): Flow<String> = flow {
        repeat(5) { index ->
            delay(1500)
            emit("Flow2: $index")
        }
    }

    fun getFlowWithOperations(): Flow<FlowOperation> = flow {
        emit(FlowOperation(stage = "LOADING", value = "Preparing..."))
        delay(500)
        emit(FlowOperation(stage = "EMITTING", value = "Data 1"))
        delay(500)
        emit(FlowOperation(stage = "TRANSFORMING", value = "Processing..."))
        delay(500)
        emit(FlowOperation(stage = "COMPLETED", value = "Done!"))
    }

    fun getRealTimeDataStream(): Flow<Int> = flow {
        var counter = 0
        while (true) {
            emit(counter++)
            delay(1000)
        }
    }

    fun getFlowWithError(): Flow<String> = flow {
        emit("Starting...")
        delay(500)
        throw Exception("Simulated error!")
    }

    suspend fun fetchDataWithRetry(): Flow<NetworkResult<String>> = flow {
        emit(NetworkResult.Loading)
        delay(1000)
        if (Random.nextFloat() > 0.3f) {
            emit(NetworkResult.Success("Data loaded!"))
        } else {
            throw Exception("Network error")
        }
    }.retryWhen { cause, attempt ->
        if (attempt < 3) {
            delay(1000 * (attempt + 1))
            true
        } else false
    }.catch { e ->
        emit(NetworkResult.Error(e.message ?: "Unknown error"))
    }
}

