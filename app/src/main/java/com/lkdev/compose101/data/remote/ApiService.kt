package com.lkdev.compose101.data.remote

import com.lkdev.compose101.model.Product
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/products")
    suspend fun products(): List<Product>

    @GET("/products/{id}")
    suspend fun productsById(@Path("id") id: Int): Product?

    companion object {
        fun create(): ApiService {
            val client = OkHttpClient.Builder().build()
            return Retrofit.Builder()
                .client(client)
                .baseUrl("https://ecommerce-product-app.herokuapp.com")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}