package com.gultendogan.storeapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.gultendogan.storeapp.data.entity.ProductEntity
import com.gultendogan.storeapp.data.entity.Products

@Dao
interface FavoriteDao {
    @Insert
    suspend fun save(product: ProductEntity)
    @Update
    suspend fun update(product: ProductEntity)

    @Query("DELETE FROM store WHERE uid=:productId")
    suspend fun deleteProduct(productId: Int)

    @Query("SELECT * FROM store")
    fun getAllProducts(): List<ProductEntity>
}