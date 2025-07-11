package com.example.challengemock.feature.product.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challengemock.domain.product.model.Product
import com.example.challengemock.domain.product.usecase.FetchProductsUseCase
import com.example.challengemock.domain.product.usecase.FilterProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val fetchProductsUseCase: FetchProductsUseCase,
    private val filterProductsUseCase: FilterProductsUseCase,
): ViewModel(), ProductListContract {

    private val mutableUIState: MutableStateFlow<ProductListContract.State> =
        MutableStateFlow(ProductListContract.State())

    private val mutableSharedFlow: MutableSharedFlow<ProductListContract.Effect> =
        MutableSharedFlow()

    override val state = mutableUIState.onStart {
            observeFilterQuery()
            event(ProductListContract.Event.Load)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = mutableUIState.value
        )

    override val effect = mutableSharedFlow.asSharedFlow()

    override fun event(event: ProductListContract.Event) {
        when (event) {
            is ProductListContract.Event.Load -> loadProducts()
            is ProductListContract.Event.OnClick -> productClicked(event.product)
            is ProductListContract.Event.OnSearchQueryChange -> {
                mutableUIState.update {
                    it.copy(searchQuery = event.query)
                }
            }
        }
    }

    private fun loadProducts() {
        mutableUIState.update { state ->
            state.copy(
                isLoading = true,
                error = null,
            )
        }

        viewModelScope.launch {
            val products = fetchProductsUseCase()

            mutableUIState.update { state ->
                state.copy(
                    products = products,
                    isLoading = false,
                    error = null,
                )
            }
        }
    }

    private fun productClicked(product: Product) {
        viewModelScope.launch {
            mutableSharedFlow.emit(ProductListContract.Effect.NavigateDetails(product))
        }
    }

    private fun observeFilterQuery() {
        state.map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500.milliseconds)
            .onEach { query ->
                when {
                    query.isBlank() -> {
                        mutableUIState.update {
                            it.copy(
                                error = null,
                                filteredProducts = emptyList()
                            )
                        }
                    }

                    query.length >= 2 -> {
                        searchProducts(query)
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun searchProducts(query: String) = viewModelScope.launch {
        val filteredProducts = filterProductsUseCase(
            FilterProductsUseCase.Params(
                query = query,
                products = state.value.products
            )
        )

        mutableUIState.update {
            it.copy(
                filteredProducts = filteredProducts,
                isLoading = false,
                error = null,
            )
        }
    }
}