package com.demotest.demo.domain.usecase

import com.demotest.demo.domain.model.Product
import com.demotest.demo.domain.repository.IProductRepository

/**
 * DOMAIN LAYER - Use Case
 * 
 * Encapsulates business logic for a specific use case
 * - Single responsibility
 * - Reusable across different UIs
 * - Easy to test
 */
class GetProductsUseCase(
    private val repository: IProductRepository
) {
    suspend operator fun invoke(): Result<List<Product>> {
        return repository.getProducts()
            .map { products ->
                // Business logic: Sort by availability and price
                products.sortedWith(
                    compareByDescending<Product> { it.available }
                        .thenBy { it.price }
                )
            }
    }
}

