package ru.skriplenok.shoppinglist.repositories.dto

import androidx.room.Embedded
import androidx.room.Relation

data class ShoppingProducts(
    // TODO выводить только name
    @Embedded
    val shopping: ShoppingDto,

    @Relation(parentColumn = "id", entityColumn = "shopping_id")
    val productList: List<ProductDto>?
) {
}