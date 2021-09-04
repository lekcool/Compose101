package com.lkdev.compose101.data.repository

import com.lkdev.compose101.data.remote.ApiService
import com.lkdev.compose101.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val service: ApiService
) {
    fun getProducts(): Flow<List<Product>> {
        return flow {
            emit(service.products())
        }
    }

    fun getProduct(id: Int): Flow<Product?> {
        return flow {
            emit(service.productsById(id))
        }
    }
}