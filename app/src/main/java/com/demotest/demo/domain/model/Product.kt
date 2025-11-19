package com.demotest.demo.domain.model

/**
 * DOMAIN LAYER - Domain Model/Entity
 * 
 * Pure business logic model
 * - No Android dependencies
 * - No framework dependencies
 * - Business rules and validation
 */
data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val category: String,
    val available: Boolean
) {
    // Business logic
    val formattedPrice: String
        get() = "$${String.format("%.2f", price)}"
    
    val isExpensive: Boolean
        get() = price > 500.0
    
    val statusText: String
        get() = if (available) "In Stock" else "Out of Stock"
}

