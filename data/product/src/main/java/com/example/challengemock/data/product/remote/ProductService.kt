package com.example.challengemock.data.product.remote

import com.example.challengemock.data.product.dto.ProductResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {

    @GET("/products")
    suspend fun fetchLazyProducts(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int,
    ): ProductResponseDto
}