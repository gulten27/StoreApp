package com.gultendogan.storeapp.repository

import com.gultendogan.storeapp.data.entity.ProductEntity
import com.gultendogan.storeapp.data.local.StoreDao
import javax.inject.Inject

class StoreRepository @Inject constructor(
    private val dbDao: StoreDao
){
    fun getAllProducts() = dbDao.getAllProducts()
    suspend fun addProduct(product: ProductEntity) = dbDao.save(product)
    suspend fun deleteProduct(productId: Int) = dbDao.deleteProduct(productId)
}