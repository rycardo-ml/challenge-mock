package com.example.challengemock.feature.product.list

import com.example.challengemock.common.error.Failure
import com.example.challengemock.domain.product.model.Product
import com.example.challengemock.ui.core.mvi.MVIContract

interface ProductListContract: MVIContract<
        ProductListContract.State,
        ProductListContract.Effect,
        ProductListContract.Event
> {

    sealed class Event {
        data object Load: Event()
        data class OnClick(val product: Product): Event()
        data class OnSearchQueryChange(val query: String): Event()
    }

    data class State(
        val products: List<Product> = emptyList(),
        val filteredProducts: List<Product> = emptyList(),
        val searchQuery: String = "",
        val isLoading: Boolean = true,
        val error: Failure? = null
    )

    sealed class Effect {
        data class NavigateDetails(val product: Product): Effect()
    }
}