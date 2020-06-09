package ru.skriplenok.shoppinglist.models

import java.util.*

data class ProductModel(
    //TODO переопределить методы equals и hashCode
    val id: Int,
    var shoppingId: Int,
    val typeId: Int,
    val name: String,
    val quantity: String,
    val createdDate: Calendar,
    var selectedDate: Calendar?,
    val typeShortName: String,
    val checked: Boolean = false // когда продукт выдран в всплывающем боковом checkbox'e
){
}