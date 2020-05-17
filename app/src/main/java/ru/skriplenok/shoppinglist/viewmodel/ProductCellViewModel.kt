package ru.skriplenok.shoppinglist.viewmodel

import ru.skriplenok.shoppinglist.models.ProductModel

interface ProductCellViewModel {

    fun getItem(position: Int): ProductModel?
    fun getQuantity(position: Int): String?
    fun getVisible(): Int
    fun itemCount(): Int
}