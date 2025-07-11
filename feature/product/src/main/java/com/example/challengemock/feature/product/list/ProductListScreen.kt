package com.example.challengemock.feature.product.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.challengemock.domain.product.model.Product
import com.example.challengemock.feature.product.list.component.ProductListUi
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreenRoot(
    viewModel: ProductListViewModel = hiltViewModel(),
    navigateToProductDetails: (Product) -> Unit,
    navigateToForm: (() -> Unit)?,
) {
    val states = viewModel.state.collectAsState().value

    val dispatch: (ProductListContract.Event) -> Unit = {
        viewModel.event(event = it)
    }

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collectLatest {
            when (it) {
                is ProductListContract.Effect.NavigateDetails -> navigateToProductDetails(it.product)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Products")
                },
                actions = {
                    if (navigateToForm != null) {
                        IconButton(onClick = { navigateToForm() }) {
                            Icon(
                                imageVector = Icons.Filled.MailOutline,
                                contentDescription = "Form screen"
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
            )
        },
    ) { innerPadding ->

        ProductListScreen(
            states = states,
            dispatch = dispatch,
            modifier =  Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun ProductListScreen(
    states: ProductListContract.State,
    dispatch: (ProductListContract.Event) -> Unit,
    modifier: Modifier = Modifier,

) {
    Box(modifier = modifier) {
        when {
            states.error != null -> Text(text = "Error while fetching")
            states.isLoading -> Text(text = "LOADING")
            states.products.isNotEmpty() -> {
                ProductListUi(
                    products =
                        if (states.searchQuery.isBlank() && states.filteredProducts.isEmpty()) states.products
                        else states.filteredProducts,
                    states = states,
                    dispatch = dispatch,
                )
            }
        }
    }
}
