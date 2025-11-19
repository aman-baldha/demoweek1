package com.demotest.demo.domain.usecase

import com.demotest.demo.domain.model.Product
import com.demotest.demo.domain.repository.IProductRepository

/**
 * DOMAIN LAYER - Use Case
 * 
 * Handles product search business logic
 */
class SearchProductsUseCase(
    private val repository: IProductRepository
) {
    suspend operator fun invoke(query: String): Result<List<Product>> {
        // Business logic: Don't search if query is too short
        if (query.length < 2) {
            return Result.success(emptyList())
        }
        
        return repository.searchProducts(query)
    }
}

