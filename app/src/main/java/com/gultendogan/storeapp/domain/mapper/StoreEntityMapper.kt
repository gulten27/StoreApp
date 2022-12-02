package com.gultendogan.storeapp.domain.mapper

import com.gultendogan.storeapp.data.entity.ProductEntity
import com.gultendogan.storeapp.data.entity.Products

class StoreEntityMapper {
    fun storeMapper(products: Products): ProductEntity{
        return ProductEntity(title = products.title, price = products.price, category = products.category,
            description = products.description, image = products.image)
    }
}