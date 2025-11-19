package com.demotest.demo.domain.repository

import com.demotest.demo.domain.model.Product
import kotlinx.coroutines.flow.Flow

/**
 * DOMAIN LAYER - Repository Interface
 * 
 * Defines contract for data operations
 * - Lives in domain layer (not data layer)
 * - Independent of implementation details
 * - Can be mocked for testing
 */
interface IProductRepository {
    suspend fun getProducts(): Result<List<Product>>
    suspend fun getProductById(id: Int): Result<Product>
    fun getProductsStream(): Flow<List<Product>>
    suspend fun searchProducts(query: String): Result<List<Product>>
}

