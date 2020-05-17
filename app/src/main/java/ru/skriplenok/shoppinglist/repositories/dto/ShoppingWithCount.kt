package ru.skriplenok.shoppinglist.repositories.dto

import androidx.room.ColumnInfo
import androidx.room.Embedded

data class ShoppingWithCount(
    @Embedded
    val shopping: ShoppingDto,

    @ColumnInfo(name = "products_all")
    val productsAll: Int,

    @ColumnInfo(name = "products_active")
    val productsActive: Int
) {}