package com.gultendogan.storeapp.data.api

import com.gultendogan.storeapp.data.entity.Categories
import com.gultendogan.storeapp.data.entity.Products
import retrofit2.http.GET

interface ApiFactory {

    @GET("products")
    suspend fun getAllProducts(
    ): List<Products>

    @GET("products/categories")
    suspend fun getAllCategories(
    ): Categories
}