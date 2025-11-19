package com.demotest.demo.data.repository

import com.demotest.demo.domain.model.Product
import com.demotest.demo.domain.repository.IProductRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * DATA LAYER - Repository Implementation
 * 
 * Responsibilities:
 * - Implements domain repository interface
 * - Handles data fetching from different sources
 * - Maps data models to domain models
 * - Handles caching and data persistence
 */
class ProductRepository : IProductRepository {

    // Simulated remote data source
    private val remoteProducts = listOf(
        ProductDto(1, "Laptop", 999.99, "Electronics", true),
        ProductDto(2, "Phone", 699.99, "Electronics", true),
        ProductDto(3, "Desk", 299.99, "Furniture", true),
        ProductDto(4, "Chair", 149.99, "Furniture", false),
        ProductDto(5, "Monitor", 399.99, "Electronics", true),
        ProductDto(6, "Keyboard", 79.99, "Electronics", true),
        ProductDto(7, "Mouse", 29.99, "Electronics", false),
        ProductDto(8, "Lamp", 49.99, "Furniture", true)
    )

    override suspend fun getProducts(): Result<List<Product>> {
        return try {
            // Simulate network delay
            delay(1000)
            
            // Simulate occasional network error
            if (Math.random() > 0.9) {
                throw Exception("Network error")
            }
            
            // Map DTO to Domain Model
            val products = remoteProducts.map { it.toDomainModel() }
            Result.success(products)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getProductById(id: Int): Result<Product> {
        return try {
            delay(500)
            
            val productDto = remoteProducts.find { it.id == id }
                ?: throw Exception("Product not found")
            
            Result.success(productDto.toDomainModel())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getProductsStream(): Flow<List<Product>> = flow {
        while (true) {
            delay(2000)
            emit(remoteProducts.map { it.toDomainModel() })
        }
    }

    override suspend fun searchProducts(query: String): Result<List<Product>> {
        return try {
            delay(500)
            
            val filtered = remoteProducts
                .filter { 
                    it.name.contains(query, ignoreCase = true) || 
                    it.category.contains(query, ignoreCase = true)
                }
                .map { it.toDomainModel() }
            
            Result.success(filtered)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

/**
 * Data Transfer Object (DTO)
 * Used in data layer, not exposed to domain/presentation
 */
private data class ProductDto(
    val id: Int,
    val name: String,
    val price: Double,
    val category: String,
    val inStock: Boolean
) {
    // Map DTO to Domain Model
    fun toDomainModel(): Product {
        return Product(
            id = id,
            name = name,
            price = price,
            category = category,
            available = inStock
        )
    }
}

