package com.gultendogan.storeapp.data.entity

data class Products(
    val id:Int,
    val title:String?,
    val price:Float?,
    val category:String?,
    val description:String?,
    val image:String?,
    var isFav:Boolean?,
    val rating: Rating?
)