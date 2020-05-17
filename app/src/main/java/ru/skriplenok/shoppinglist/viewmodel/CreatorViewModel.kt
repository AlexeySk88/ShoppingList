package ru.skriplenok.shoppinglist.viewmodel

import android.content.Context
import android.view.View
import android.widget.ArrayAdapter
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.runBlocking
import ru.skriplenok.shoppinglist.R
import ru.skriplenok.shoppinglist.adapters.ProductsAdapter
import ru.skriplenok.shoppinglist.helpers.Constants
import ru.skriplenok.shoppinglist.models.ProductsModel
import ru.skriplenok.shoppinglist.models.QuantityTypeModel
import ru.skriplenok.shoppinglist.repositories.ProductTypeRepository

class CreatorViewModel(
    typeRepository: ProductTypeRepository,
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

    private var quantityTypes: List<QuantityTypeModel> = mutableListOf()
    private val productList: MutableList<ProductsModel> = mutableListOf()

    init {
        runBlocking {
            quantityTypes = typeRepository.getAll()
        }
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
        for(quantityType in quantityTypes) {
            spinnerTypes.add(quantityType.shortName)
        }
        spinnerAdapter = ArrayAdapter(context, R.layout.spinner_item, spinnerTypes)
        spinnerAdapter?.setDropDownViewResource(R.layout.spinner_item)
    }

    override fun getItem(position: Int): ProductsModel? {
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
        val type = quantityTypes[indexType.get()]
        val quantity = count.get() + " " + type.shortName
        val productModel = ProductsModel(0, name.get()!!, quantity, false)
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