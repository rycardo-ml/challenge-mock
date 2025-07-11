package com.example.challengemock.domain.product.usecase

import com.example.challengemock.domain.product.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.Normalizer
import javax.inject.Inject

class FilterProductsUseCase  @Inject constructor(

) {
    suspend operator fun invoke(params: Params): List<Product> = withContext(Dispatchers.Default) {
        val regex = (
                buildRegexString(params.query) +
                ".*"
        ).toRegex()

        val filtered = params.products.filter {
            val name = removeAccents(it.name)
            val description = removeAccents(it.description)

            regex.containsMatchIn(name) || regex.containsMatchIn(description)
        }

        return@withContext filtered
    }

    private fun buildRegexString(query: String) =
        query.split(" ").joinToString(separator = "") {
            "(?i)(?=.*$it)"
        }

    private fun normalize(input: String): String {
        return Normalizer.normalize(input, Normalizer.Form.NFKD)
    }

    private fun removeAccents(input: String): String {
        return normalize(input).replace("\\p{M}".toRegex(), "")
    }

    data class Params(
        val products: List<Product>,
        val query: String,
    )
}