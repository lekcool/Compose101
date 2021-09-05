package com.lkdev.compose101

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.lkdev.compose101.ui.ProductDetailScreen
import com.lkdev.compose101.ui.ProductScreen
import com.lkdev.compose101.ui.theme.Compose101Theme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalFoundationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            Compose101Theme(false) {
                Scaffold {
                    NavHost(
                        navController = navController,
                        startDestination = "product",
                        modifier = Modifier.padding(it)
                    ) {
                        composable(
                            route = "product",
                            content = {
                                ProductScreen(navController = navController)
                            }
                        )
                        composable(
                            route = "product/{productId}",
                            arguments = listOf(navArgument("productId") {
                                type = NavType.IntType
                            }),
                            content = {
                                ProductDetailScreen(navController = navController)
                            }
                        )
                    }
                }
            }
        }
    }
}