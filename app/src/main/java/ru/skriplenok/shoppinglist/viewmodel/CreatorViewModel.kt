package ru.skriplenok.shoppinglist.viewmodel

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModel
import ru.skriplenok.shoppinglist.R
import ru.skriplenok.shoppinglist.adapters.ProductsAdapter
import ru.skriplenok.shoppinglist.models.ProductsModel
import ru.skriplenok.shoppinglist.models.QuantityType

class CreatorViewModel: ViewModel(), ProductCellViewModel {

    val adapter: ProductsAdapter = ProductsAdapter(R.layout.product_cell, this)
    var spinnerAdapter: ArrayAdapter<String>? = null

    private val productList: MutableList<ProductsModel> = mutableListOf()

    fun init(context: Context) {
        val spinnerTypes: MutableList<String> = mutableListOf()
        for(quantityType in QuantityType.values()) {
            spinnerTypes.add(quantityType.value.name)
        }
        spinnerAdapter = ArrayAdapter(context, R.layout.spinner_item, spinnerTypes)
        spinnerAdapter!!.setDropDownViewResource(R.layout.spinner_item)
    }

    fun setModelInAdapter() = adapter.notifyDataSetChanged()

    override fun getItem(position: Int): ProductsModel? {
        Log.d("CLICK", "SAVE")
        if(position < productList.size) {
            return productList[position]
        }
        return null
    }

    override fun getVisible(): Int = View.GONE

    override fun itemCount(): Int = productList.size

    fun onClickSave() {
        val productModel = ProductsModel(0, "Сахар", "3 шт.", false)
        productList.add(productModel)

        adapter.notifyItemInserted(itemCount() - 1)
    }
}