package ru.skriplenok.shoppinglist.models

import ru.skriplenok.shoppinglist.repositories.dto.ShoppingDto

class ShoppingModel(
    //TODO разложить shopping на отдельные поля
    val shopping: ShoppingDto,
    val productsAll: Int,
    //TODO переименовать в productSelected
    val productsActive: Int,
    val checked: Boolean = false // когда список выбран в всплывающем боковом checkbox'e
) {}