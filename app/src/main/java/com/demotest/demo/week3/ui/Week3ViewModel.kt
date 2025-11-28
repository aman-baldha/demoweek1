package com.demotest.demo.week3.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.demotest.demo.week3.data.UserWithPosts
import com.demotest.demo.week3.data.Week3Repository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class Week3ViewModel(private val repository: Week3Repository) : ViewModel() {

    val uiState: StateFlow<List<UserWithPosts>> = repository.usersWithPosts
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun refreshData() {
        viewModelScope.launch {
            repository.refreshData()
        }
    }
}

class Week3ViewModelFactory(private val repository: Week3Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(Week3ViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return Week3ViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
