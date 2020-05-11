package ru.skriplenok.shoppinglist.viewmodel

import ru.skriplenok.shoppinglist.models.ProductsModel

interface ProductCellViewModel {

    fun getItem(position: Int): ProductsModel?
    fun getVisible(): Boolean
}