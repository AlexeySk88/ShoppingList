package ru.skriplenok.shoppinglist.models

data class ProductModel(
    val id: Int,
    val name: String,
    val quantity: String,
    val type: String,
    var status: Boolean
){
}