package ru.skriplenok.shoppinglist.models

data class CreatorModel(
    var title: String?,
    var productList: MutableList<ProductModel>,
    var productName: String?,
    var productQuantity: String?,
    var indexType: Int
) {
}