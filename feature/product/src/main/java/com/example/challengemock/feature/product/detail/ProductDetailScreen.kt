package com.example.challengemock.feature.product.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.challengemock.domain.product.model.Product
import com.example.challengemock.feature.product.detail.ExtensionDensity.toDp
import com.example.challengemock.feature.product.detail.ExtensionDensity.toPx
import com.example.challengemock.feature.product.detail.components.CollapsibleHeader

@Composable
fun ProductDetailScreen(
    viewModel: ProductDetailViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    state.product?.let {
        ProductDetailsUI(product = it)
    } ?: ProductDetailsNotFound()
}

@Composable
private fun ProductDetailsNotFound() {
    Text("Product Not found")
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ProductDetailsUI(
    product: Product
) {

    BoxWithConstraints {
        val constraints = this

        CollapsibleHeader(
            modifier = Modifier.fillMaxSize(),
            properties = CollapsibleHeader.Properties(
                min = (constraints.maxHeight.toPx * .4f).toDp,
                max = (constraints.maxHeight.toPx * .7f).toDp
            ),
            header = { progress, progressDp ->
                Column(
                    modifier = Modifier.fillMaxWidth().height(progressDp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    val internalProgress = (1 - (1 - progress) / 9)

                    GlideImage(
                        model = product.image,
                        contentDescription = "",
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .fillMaxSize(internalProgress)
                            .padding(8.dp)
                    )
                }
            },
            body = {
                Text(
                    text = "Produto: ${product.name}",
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp)
                )

                Text(
                    text = "Preço: ${product.price}",
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp)
                )

                Text(
                    text = "Desconto: ${product.discount}",
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp)
                )

                Text(
                    text = "Estoque: ${product.stock}",
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp)
                )

                Text(
                    text = "Rating: ${product.rating}",
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp)
                )
            }
        )
    }
}

object ExtensionDensity {

    inline val Dp.toPx @Composable get() = this.toPx(LocalDensity.current.density)
    fun Dp.toPx(density: Float) = value * density

    val Int.toDp @Composable get() = this.toDp(LocalDensity.current.density)
    fun Int.toDp(density: Float) = Dp((this / density))

    val Float.toDp @Composable get() = this.toDp(LocalDensity.current.density)
    fun Float.toDp(density: Float) = Dp((this / density))

}