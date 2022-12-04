package com.gultendogan.storeapp.repository

import com.gultendogan.storeapp.data.api.ApiFactory
import com.gultendogan.storeapp.data.entity.ProductEntity
import com.gultendogan.storeapp.data.entity.Products
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val apiFactory: ApiFactory
) {

    suspend fun getProducts(): List<Products> {
        return apiFactory.getAllProducts()
    }

}