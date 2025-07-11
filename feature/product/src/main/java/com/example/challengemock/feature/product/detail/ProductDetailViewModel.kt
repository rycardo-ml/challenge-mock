package com.example.challengemock.feature.product.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.challengemock.domain.product.usecase.GetProductUseCase
import com.example.challengemock.ui.core.navigation.RouteProductDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val getProductUseCase: GetProductUseCase,
): ViewModel(), ProductDetailContract {

    private val productId = savedStateHandle.toRoute<RouteProductDetail>().id

    private val mutableUIState: MutableStateFlow<ProductDetailContract.State> =
        MutableStateFlow(ProductDetailContract.State())

    private val mutableSharedFlow: MutableSharedFlow<ProductDetailContract.Effect> =
        MutableSharedFlow()

    override val state: StateFlow<ProductDetailContract.State>
        get() = mutableUIState.onStart {
            event(ProductDetailContract.Event.Load)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProductDetailContract.State()
        )

    override val effect: SharedFlow<ProductDetailContract.Effect>
        get() = mutableSharedFlow.asSharedFlow()

    override fun event(event: ProductDetailContract.Event) {
        when (event) {
            is ProductDetailContract.Event.Load -> loadDetails()
        }
    }

    private fun loadDetails() {
        viewModelScope.launch {
            val product = getProductUseCase(productId)

            mutableUIState.update { state ->
                state.copy(
                    product = product
                )
            }
        }
    }
}