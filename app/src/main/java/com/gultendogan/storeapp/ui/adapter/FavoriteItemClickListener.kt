package com.gultendogan.storeapp.ui.adapter

import com.gultendogan.storeapp.data.entity.ProductEntity

interface FavoriteItemClickListener {
    fun onItemClick(productEntity: ProductEntity)
}