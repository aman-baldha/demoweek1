package com.demotest.demo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demotest.demo.data.repository.ProductRepository
import com.demotest.demo.domain.model.Product
import com.demotest.demo.domain.usecase.GetProductsUseCase
import com.demotest.demo.domain.usecase.SearchProductsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * PRESENTATION LAYER - ViewModel
 * 
 * Responsibilities:
 * - Holds UI state
 * - Processes user intents/events
 * - Calls use cases from domain layer
 * - Transforms domain models to UI state
 * - No business logic here!
 */
class LayeredArchViewModel : ViewModel() {

    // Dependencies (in real app, these would be injected via DI like Hilt)
    private val repository = ProductRepository()
    private val getProductsUseCase = GetProductsUseCase(repository)
    private val searchProductsUseCase = SearchProductsUseCase(repository)

    // UI State
    data class UiState(
        val products: List<Product> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null,
        val searchQuery: String = "",
        val selectedCategory: String = "All",
        val lastAction: String = "Ready"
    )

    // User Intents
    sealed class UserIntent {
        data object LoadProducts : UserIntent()
        data class SearchProducts(val query: String) : UserIntent()
        data class FilterByCategory(val category: String) : UserIntent()
        data object RefreshProducts : UserIntent()
    }

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        loadProducts()
    }

    /**
     * UNIDIRECTIONAL DATA FLOW Entry Point
     * All user actions flow through here
     */
    fun processIntent(intent: UserIntent) {
        when (intent) {
            is UserIntent.LoadProducts -> loadProducts()
            is UserIntent.SearchProducts -> searchProducts(intent.query)
            is UserIntent.FilterByCategory -> filterByCategory(intent.category)
            is UserIntent.RefreshProducts -> refreshProducts()
        }
    }

    private fun loadProducts() {
        viewModelScope.launch {
            // Update state: Loading started
            _state.value = _state.value.copy(
                isLoading = true,
                error = null,
                lastAction = "Loading products..."
            )

            // Call domain layer (use case)
            val result = getProductsUseCase()

            // Update state: Loading finished
            _state.value = result.fold(
                onSuccess = { products ->
                    _state.value.copy(
                        products = products,
                        isLoading = false,
                        lastAction = "Loaded ${products.size} products"
                    )
                },
                onFailure = { error ->
                    _state.value.copy(
                        isLoading = false,
                        error = error.message ?: "Unknown error",
                        lastAction = "Error loading products"
                    )
                }
            )
        }
    }

    private fun searchProducts(query: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                searchQuery = query,
                isLoading = true,
                lastAction = "Searching for '$query'..."
            )

            val result = searchProductsUseCase(query)

            _state.value = result.fold(
                onSuccess = { products ->
                    _state.value.copy(
                        products = products,
                        isLoading = false,
                        lastAction = "Found ${products.size} results"
                    )
                },
                onFailure = { error ->
                    _state.value.copy(
                        isLoading = false,
                        error = error.message,
                        lastAction = "Search failed"
                    )
                }
            )
        }
    }

    private fun filterByCategory(category: String) {
        _state.value = _state.value.copy(
            selectedCategory = category,
            lastAction = "Filtered by $category"
        )
    }

    private fun refreshProducts() {
        loadProducts()
    }
}

