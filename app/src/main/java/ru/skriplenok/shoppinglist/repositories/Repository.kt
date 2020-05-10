package ru.skriplenok.shoppinglist.repositories

import ru.skriplenok.shoppinglist.models.ProductsModel
import ru.skriplenok.shoppinglist.models.ShoppingModel

interface Repository {

    fun fetchShoppingList(): List<ShoppingModel>
    fun fetchProductList(id: Int): List<ProductsModel>
}