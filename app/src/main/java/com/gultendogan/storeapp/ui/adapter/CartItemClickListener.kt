package com.gultendogan.storeapp.ui.adapter

import com.gultendogan.storeapp.data.entity.ProductEntity

interface CartItemClickListener {
    fun onItemClick(productEntity: ProductEntity)
}