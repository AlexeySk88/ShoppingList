package ru.skriplenok.shoppinglist.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import ru.skriplenok.shoppinglist.BR

class CreatorModel(
    var title: String?,
    var productList: MutableList<ProductModel>,
    _productName: String?,
    _productQuantity: String?,
    var indexType: Int
): BaseObservable() {

    @Bindable
    var productName: String? = _productName
    set(value) {
        field = value
        notifyPropertyChanged(BR.productName)
    }

    @Bindable
    var productQuantity: String? = _productQuantity
    set(value) {
        field = value
        notifyPropertyChanged(BR.productQuantity)
    }
}