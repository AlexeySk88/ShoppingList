package ru.skriplenok.shoppinglist.models

import ru.skriplenok.shoppinglist.repositories.dto.ProductDto

data class ProductModel(
    //TODO разложить product на отдельные поля, после этого переопределить методы equals и hashCode
    val product: ProductDto,
    val typeShortName: String,
    val checked: Boolean = false // когда продукт выдран в всплывающем боковом checkbox'e
){
}