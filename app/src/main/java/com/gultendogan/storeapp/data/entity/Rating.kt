package com.gultendogan.storeapp.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rating (
    val rate:Float,
    val count:Int
): Parcelable