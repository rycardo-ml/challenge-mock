package com.example.challengemock.domain.product.repository

import com.example.challengemock.domain.product.model.Product

interface ProductRepository {

    suspend fun fetch(): List<Product>

    suspend fun getById(id: Long): Product

}