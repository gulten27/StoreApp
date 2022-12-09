package com.gultendogan.storeapp.data.local

import androidx.room.*
import com.gultendogan.storeapp.data.entity.ProductEntity

@Dao
interface CartDao {
    @Insert
    suspend fun save(product: ProductEntity)
    @Update
    suspend fun update(product: ProductEntity)

    @Query("DELETE FROM store WHERE uid=:productId")
    suspend fun deleteProduct(productId: Int)

    @Query("SELECT * FROM store")
    fun getAllProducts(): List<ProductEntity>
}