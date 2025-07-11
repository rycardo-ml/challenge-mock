package com.example.challengemock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.challengemock.feature.form.FormScreenRoot
import com.example.challengemock.feature.product.detail.ProductDetailScreen
import com.example.challengemock.feature.product.list.ProductListScreenRoot
import com.example.challengemock.ui.core.navigation.RouteForm
import com.example.challengemock.ui.core.navigation.RouteProductDetail
import com.example.challengemock.ui.core.navigation.RouteProductList
import com.example.challengemock.ui.core.theme.ChallengeMockTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val firstRoute = remember {
                if (BuildConfig.FLAVOR == "form") RouteForm
                else RouteProductList
            }

            Box(Modifier.safeDrawingPadding()) {
                ChallengeMockTheme  {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = firstRoute,
                    ) {
                        composable<RouteProductList> {
                            ProductListScreenRoot(
                                navigateToProductDetails = {
                                    navController.navigate(
                                        RouteProductDetail(
                                            id = it.id
                                        )
                                    )
                                },
                                navigateToForm =
                                    if (BuildConfig.FLAVOR != "complete") null
                                    else {
                                        {
                                            navController.navigate(
                                                RouteForm
                                            )
                                        }
                                    }
                            )
                        }

                        composable<RouteProductDetail>{
                            ProductDetailScreen()
                        }

                        composable<RouteForm> {
                            FormScreenRoot()
                        }
                    }
                }
            }
        }
    }
}
