package com.lkdev.compose101.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.lkdev.compose101.R
import com.lkdev.compose101.model.Product
import com.lkdev.compose101.ui.theme.TextNew2
import com.lkdev.compose101.ui.theme.TextPrice

@Composable
fun ProductDetailScreen(
    navController: NavController,
    viewModel: ProductDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Column(Modifier.background(Color.White)) {
        TopAppBar(
            title = { Text(text = "Detail", color = Color.White) },
            navigationIcon = {
                IconButton(onClick = navController::popBackStack) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = null)
                }
            }
        )
        when {
            state.loading -> {
                LoadingCenter()
            }
            state.errorMessage != null -> {
                MessageCenter(message = state.errorMessage ?: "Something Error")
            }
            else -> {
                state.product?.let {
                    ProductDetail(product = it)
                } ?: run {
                    MessageCenter(message = "ไม่พบข้อมูล")
                }
            }
        }
    }
}

@Composable
fun ProductDetail(product: Product, modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    Column(
        modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.size(32.dp))
        Box(contentAlignment = Alignment.TopEnd) {
            Image(
                painter = rememberImagePainter(
                    data = product.image,
                    builder = {
                        placeholder(R.drawable.ic_launcher_background)
                        error(R.drawable.ic_launcher_background)
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1F)
            )

            if (product.isNewProduct) {
                Text(
                    text = "NEW",
                    style = TextNew2,
                    modifier = modifier
                        .padding(8.dp)
                )
            }
        }
        Text(
            text = product.title,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = product.getPriceFormat(),
            style = TextPrice,
            color = Color.Red
        )
        Text(
            text = product.content,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(top = 8.dp)
        )
        Spacer(modifier = Modifier.size(32.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ProductDetailPreview() {
    ProductDetail(
        product = Product(
            id = 1,
            title = "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC",
            image = "https://firebasestorage.googleapis.com/v0/b/test-4c60c.appspot.com/o/cookie2%402x.png?alt=media&token=f8a2a013-8a6e-44b6-b66d-feb20bfc6a51",
            content = "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of.",
            isNewProduct = true,
            price = 9.212
        )
    )
}