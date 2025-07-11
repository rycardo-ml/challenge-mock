package com.example.challengemock.data.product.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    @SerialName("id") val id: Long,
    @SerialName("title") val name: String,
    @SerialName("description") val description: String,
    @SerialName("rating") val rating: Float,
    @SerialName("price") val price: Double,
    @SerialName("discountPercentage") val discount: Double,
    @SerialName("stock") val stock: Int,
    @SerialName("images") val images: List<String>,
)