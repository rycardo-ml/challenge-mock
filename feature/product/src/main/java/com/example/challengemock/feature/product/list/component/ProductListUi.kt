package com.example.challengemock.feature.product.list.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.example.challengemock.domain.product.model.Product
import com.example.challengemock.feature.product.list.ProductListContract

@Composable
fun ProductListUi(
    products: List<Product>,
    states: ProductListContract.State,
    dispatch: (ProductListContract.Event) -> Unit,
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
    ) {
        BookSearchBar(
            searchQuery = states.searchQuery,
            onSearchQueryChange = {
                dispatch(ProductListContract.Event.OnSearchQueryChange(it))
            },
            onImeSearch = {
                keyboardController?.hide()
            },
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth()
                .padding(16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            items(products.size) { index ->
                products[index].let {
                    ProductCard(
                        productName = it.name,
                        rating = it.rating,
                        ratingLevel = it.ratingLevel,
                        modifier = Modifier
                            .height(100.dp)
                            .clickable {
                                dispatch.invoke(ProductListContract.Event.OnClick(it))
                            }
                    )
                }
            }
        }
    }
}