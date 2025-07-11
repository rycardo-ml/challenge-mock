package com.example.challengemock.feature.product.list.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.challengemock.domain.product.model.RatingLevel
import com.example.challengemock.feature.product.R

@Composable
fun ProductCard(
    productName: String,
    rating: Float,
    ratingLevel: RatingLevel,
    modifier: Modifier = Modifier,
) {

    val ratingImage by remember (ratingLevel) {
        derivedStateOf {
            when (ratingLevel) {
                RatingLevel.LOW -> R.drawable.ic_rating_low
                RatingLevel.MID -> R.drawable.ic_rating_mid
                RatingLevel.HIGH -> R.drawable.ic_rating_high
            }
        }
    }

    Card(
        modifier = modifier
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {

            Text(
                text = productName,
                fontSize = 16.sp,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1f)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(start = 8.dp)
            ) {

                Image(
                    imageVector = ImageVector.vectorResource(ratingImage),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(vertical = 2.dp)
                        .size(16.dp)
                )

                Text(
                    text = "($rating)",
                    fontSize = 9.sp,
                )
            }
        }
    }
}

@Preview
@Composable
private fun ProductCardPreview() {
    ProductCard(
        productName = "Product and more text and ll a .. little ++ more --",
        rating = 3F,
        ratingLevel = RatingLevel.MID,
        modifier = Modifier
            .width(150.dp)
            .height(75.dp)

    )
}
