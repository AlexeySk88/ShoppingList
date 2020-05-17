package ru.skriplenok.shoppinglist.viewmodel

import android.view.View
import ru.skriplenok.shoppinglist.models.ProductsModel
import ru.skriplenok.shoppinglist.repositories.dto.ProductDto

interface ProductCellViewModel {

    fun getItem(position: Int): ProductsModel?
    fun getVisible(): Int
    fun itemCount(): Int
}