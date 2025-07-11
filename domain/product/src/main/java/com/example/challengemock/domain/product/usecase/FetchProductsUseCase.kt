package com.example.challengemock.domain.product.usecase

import com.example.challengemock.domain.product.repository.ProductRepository
import javax.inject.Inject

class FetchProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository,
) {

    suspend operator fun invoke() = productRepository.fetch()

}