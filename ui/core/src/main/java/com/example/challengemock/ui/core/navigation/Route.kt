package com.example.challengemock.ui.core.navigation

import kotlinx.serialization.Serializable

@Serializable
object RouteProductList

@Serializable
data class RouteProductDetail(
    val id: Long,
)

@Serializable
object RouteForm