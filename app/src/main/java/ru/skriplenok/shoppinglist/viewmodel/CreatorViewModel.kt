package ru.skriplenok.shoppinglist.viewmodel

import android.content.Context
import android.view.View
import android.widget.ArrayAdapter
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import ru.skriplenok.shoppinglist.R
import ru.skriplenok.shoppinglist.adapters.ProductsAdapter
import ru.skriplenok.shoppinglist.helpers.Constants
import ru.skriplenok.shoppinglist.helpers.QuantityTypes
import ru.skriplenok.shoppinglist.models.ProductModel

class CreatorViewModel(
    context: Context,
    handle: SavedStateHandle
): ViewModel(), ProductCellViewModel {

    val adapter: ProductsAdapter = ProductsAdapter(R.layout.product_cell, this)
    var spinnerAdapter: ArrayAdapter<String>? = null
    val title: ObservableField<String> = ObservableField()
    val productsNumber: ObservableField<String> = ObservableField()
        get() {
            if (productList.size > 0 ) {
                field.set("Товаров в списке: " + productList.size)
            } else {
                field.set("Добавьте в список хотя бы один товар")
            }
            return field
        }
    val name: ObservableField<String> = ObservableField()
    val count: ObservableField<String> = ObservableField()
    val indexType: ObservableInt = ObservableInt()
    val toastMessage: MutableLiveData<String> = MutableLiveData()

    private var quantityTypes: QuantityTypes = QuantityTypes.getInstance()
    private val productList: MutableList<ProductModel> = mutableListOf()

    init {
        setSpinnerAdapter(context)
        setProductsNumber()
    }

    private fun setProductsNumber() {
        if (productList.size > 0 ) {
            productsNumber.set("Товаров в списке: " + productList.size)
            return
        }
        productsNumber.set("Добавьте в список хотя бы один товар")
    }

    private fun setSpinnerAdapter(context: Context) {
        val spinnerTypes: MutableList<String> = mutableListOf()
        for(quantityType in quantityTypes.list) {
            spinnerTypes.add(quantityType.fullName)
        }
        spinnerAdapter = ArrayAdapter(context, R.layout.spinner_item, spinnerTypes)
        spinnerAdapter?.setDropDownViewResource(R.layout.spinner_item)
    }

    override fun getItem(position: Int): ProductModel? {
        if(position < productList.size) {
            return productList[position]
        }
        return null
    }

    override fun getVisible(): Int = View.GONE

    override fun itemCount(): Int = productList.size

    fun onClickSave() {
        if (!validateProduct()) {
            return
        }
        val type = quantityTypes.list[indexType.get()]
        val productModel = ProductModel(0, name.get()!!, count.get()!!, type.shortName, false)
        productList.add(productModel)
        adapter.notifyItemInserted(itemCount() - 1)
    }

    private fun validateProduct(): Boolean {
        val sb = StringBuilder()
        if (name.get().isNullOrEmpty()) {
            sb.append(formatItemValidateMessage(Constants.NAME_PRODUCT.value))
        }
        if (count.get().isNullOrEmpty()) {
            sb.append(formatItemValidateMessage(Constants.COUNT_PRODUCT.value))
        }

        if (sb.isNotEmpty()) {
            sb.insert(0, Constants.CREATOR_NOT_VALIDATED.value + ":")
            toastMessage.value  = sb.toString()
            return false
        }
        return true
    }

    private fun formatItemValidateMessage(item: String) = "\n\t•$item"

    fun onClickShoppingSave() {
        validateShopping()
    }

    private fun validateShopping(): Boolean {
        val sb = StringBuilder()
        if (title.get().isNullOrEmpty()) {
            sb.append(formatItemValidateMessage(Constants.NAME_SHOPPING.value))
        }
        if (productList.size == 0) {
            sb.append(formatItemValidateMessage(Constants.PRODUCT_COUNT.value))
        }

        if (sb.isNotEmpty()) {
            sb.insert(0, Constants.CREATOR_NOT_VALIDATED.value + ":")
            toastMessage.value = sb.toString()
            return false
        }
        return true
    }
}