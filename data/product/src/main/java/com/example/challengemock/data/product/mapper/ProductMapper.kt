package com.example.challengemock.data.product.mapper

import com.example.challengemock.data.database.entity.ProductEntity
import com.example.challengemock.data.product.dto.ProductDto
import com.example.challengemock.domain.product.model.Product
import com.example.challengemock.domain.product.model.RatingLevel

fun ProductEntity.toModel() = Product(
    id = id,
    name = name,
    description = description,
    rating = rating,
    ratingLevel = getRatingLevel(rating),
    price = price,
    discount = discount,
    stock = stock,
    image = image
)

fun ProductDto.toEntity() = ProductEntity(
    id = id,
    name = name,
    description = description,
    rating = rating,
    price = price,
    discount = discount,
    stock = stock,
    image = images.firstOrNull()
)

private fun getRatingLevel(rating: Float) = when {
    (rating < 3f) -> RatingLevel.LOW
    (rating > 4f) -> RatingLevel.HIGH
    else -> RatingLevel.MID
}