package ru.skriplenok.shoppinglist.viewmodel

import android.content.Context
import android.view.View
import android.widget.ArrayAdapter
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import ru.skriplenok.shoppinglist.R
import ru.skriplenok.shoppinglist.adapters.ProductsAdapter
import ru.skriplenok.shoppinglist.models.ProductsModel
import ru.skriplenok.shoppinglist.models.QuantityType

class CreatorViewModel: ViewModel(), ProductCellViewModel {

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

    private val productList: MutableList<ProductsModel> = mutableListOf()

    fun init(context: Context) {
        setProductsNumber()
        setSpinnerAdapter(context)
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
        for(quantityType in QuantityType.values()) {
            spinnerTypes.add(quantityType.value.name)
        }
        spinnerAdapter = ArrayAdapter(context, R.layout.spinner_item, spinnerTypes)
        spinnerAdapter!!.setDropDownViewResource(R.layout.spinner_item)
    }

    fun setModelInAdapter() = adapter.notifyDataSetChanged()

    override fun getItem(position: Int): ProductsModel? {
        if(position < productList.size) {
            return productList[position]
        }
        return null
    }

    override fun getVisible(): Int = View.GONE

    override fun itemCount(): Int = productList.size

    fun onClickSave() {
        val type = QuantityType.values()[indexType.get()]
        val quantity = count.get() + " " + type.value.shortName
        val productModel = ProductsModel(0, name.get(), quantity, false)
        productList.add(productModel)
        adapter.notifyItemInserted(itemCount() - 1)

        setProductsNumber()
    }
}