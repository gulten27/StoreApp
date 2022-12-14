package com.gultendogan.storeapp.ui.adapter

import com.gultendogan.storeapp.data.entity.Products

interface FavoriteItemClickListener {
    fun onItemClick(products: Products)
    fun onCartItemClick(product : Products)
}