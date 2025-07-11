package com.example.challengemock.feature.product.detail

import com.example.challengemock.common.error.Failure
import com.example.challengemock.domain.product.model.Product
import com.example.challengemock.ui.core.mvi.MVIContract

interface ProductDetailContract: MVIContract<
        ProductDetailContract.State,
        ProductDetailContract.Effect,
        ProductDetailContract.Event
> {

    data class State(
        val product: Product? = null,
        val isLoading: Boolean = true,
        val error: Failure? = null
    )

    sealed class Effect {

    }

    sealed class Event {
        data object Load: Event()
    }
}