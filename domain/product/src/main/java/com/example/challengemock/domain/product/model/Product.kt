package com.example.challengemock.domain.product.model

data class Product(
    val id: Long,
    val name: String,
    val description: String,
    val rating: Float,
    val ratingLevel: RatingLevel,
    val price: Double,
    val discount: Double,
    val stock: Int,
    val image: String?,
)

enum class RatingLevel {
    LOW,
    MID,
    HIGH
}