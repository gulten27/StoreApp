package com.gultendogan.storeapp.ui.adapter

import com.gultendogan.storeapp.data.entity.Products

interface ItemClickListener {

    fun onItemClick(product : Products)
    fun favOnItemClick(product: Products)
    fun onFragmentItemClick(product: Products)
}