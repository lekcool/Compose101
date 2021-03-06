package com.lkdev.compose101.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.lkdev.compose101.R
import com.lkdev.compose101.model.Product
import com.lkdev.compose101.ui.theme.TextNew
import com.lkdev.compose101.ui.theme.TextPrice

@ExperimentalFoundationApi
@Composable
fun ProductScreen(navController: NavController, viewModel: ProductViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    Column {
        TopAppBar(title = {
            Text(text = "Products", color = Color.White)
        })
        when {
            state.loading -> {
                LoadingCenter()
            }
            state.errorMessage != null -> {
                MessageCenter(message = state.errorMessage ?: "Something Error")
            }
            else -> {
                if (state.products.isEmpty()) {
                    MessageCenter(message = "?????????????????????????????????")
                } else {
                    ProductList(
                        products = state.products,
                        productClick = {
                            navController.navigate("product/${it.id}")
                        }
                    )
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun ProductList(
    products: List<Product>,
    productClick: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        content = {
            items(products) {
                ProductItem(product = it, onCLick = { productClick.invoke(it) })
            }
        },
        modifier = modifier,
        contentPadding = PaddingValues(8.dp)
    )
}

@Composable
fun ProductItem(product: Product, onCLick: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier
            .padding(8.dp)
            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
            .clickable(indication = null,
                interactionSource = remember { MutableInteractionSource() }) {
                onCLick.invoke()
            }
            .padding(16.dp)
    ) {
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
                    style = TextNew,
                    modifier = modifier
                        .padding(8.dp)
                )
            }
        }
        Text(
            text = product.title,
            style = MaterialTheme.typography.subtitle1,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = product.getPriceFormat(),
            style = TextPrice,
            color = Color.Red
        )
    }
}

@Composable
fun LoadingCenter() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun MessageCenter(message: String) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Text(text = message)
    }
}

@Preview(showBackground = true)
@Composable
fun ProductItemPreview() {
    ProductItem(
        product = Product(
            id = 1,
            title = "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC",
            image = "https://firebasestorage.googleapis.com/v0/b/test-4c60c.appspot.com/o/cookie2%402x.png?alt=media&token=f8a2a013-8a6e-44b6-b66d-feb20bfc6a51",
            content = "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of.",
            isNewProduct = true,
            price = 9.21
        ),
        onCLick = { }
    )
}