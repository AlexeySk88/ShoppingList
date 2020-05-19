package ru.skriplenok.shoppinglist.models

import ru.skriplenok.shoppinglist.helpers.QuantityTypes
import ru.skriplenok.shoppinglist.repositories.dto.ProductDto

data class ProductModel(
    val product: ProductDto,
    val typeShortName: String,
    val checked: Boolean = false // когда продукт выдран в всплывающем боковом checkbox'e
){
}