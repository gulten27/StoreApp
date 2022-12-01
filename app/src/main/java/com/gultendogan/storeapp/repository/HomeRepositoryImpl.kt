package com.gultendogan.storeapp.repository

import com.gultendogan.storeapp.data.ApiFactory
import com.gultendogan.storeapp.data.Products
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val apiFactory: ApiFactory
) {

    suspend fun getProducts(): List<Products> {
        return apiFactory.getAllProducts()
    }

}