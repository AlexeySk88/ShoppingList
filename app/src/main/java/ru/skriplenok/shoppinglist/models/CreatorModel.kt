package ru.skriplenok.shoppinglist.models

import androidx.lifecycle.MutableLiveData

data class CreatorModel(
    var title: String?,
    var productList: MutableList<ProductModel>,
    var productName: MutableLiveData<String>,
    var productQuantity: MutableLiveData<String>,
    var indexType: Int
) {
}