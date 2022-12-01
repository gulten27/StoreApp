package com.gultendogan.storeapp.data

import retrofit2.Response
import retrofit2.http.GET

interface ApiFactory {

    @GET("products")
    suspend fun getAllProducts(
    ): List<Products>

}