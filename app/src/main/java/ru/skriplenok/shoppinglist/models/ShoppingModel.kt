package ru.skriplenok.shoppinglist.models

import ru.skriplenok.shoppinglist.repositories.dto.ShoppingDto

class ShoppingModel(
    val shopping: ShoppingDto,
    val productsAll: Int,
    val productsActive: Int,
    val checked: Boolean
) {}