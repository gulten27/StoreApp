package com.gultendogan.storeapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gultendogan.storeapp.data.entity.ProductEntity

@Database(
    entities = [ProductEntity::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun storeDao(): StoreDao
}