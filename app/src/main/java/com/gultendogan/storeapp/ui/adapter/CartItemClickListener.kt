package com.gultendogan.storeapp.ui.adapter

import com.gultendogan.storeapp.data.entity.Products

interface CartItemClickListener {
    fun onItemClick(product: Products)
}