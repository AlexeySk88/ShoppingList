package ru.skriplenok.shoppinglist.models

data class ProductsModel(
    val id: Int,
    val name: String,
    val quantity: String,
//    val type: QuantityType,
    var status: Boolean
){
}