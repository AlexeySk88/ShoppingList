package ru.skriplenok.shoppinglist.viewmodel

import ru.skriplenok.shoppinglist.models.ProductModel

interface ProductCellViewModel {

    fun getTitle(position: Int): String?
    fun getSelected(position: Int): Boolean
    fun getQuantity(position: Int): String?
    fun getVisible(): Int
    fun itemCount(): Int
}