package ru.skriplenok.shoppinglist.viewmodel

import androidx.lifecycle.ViewModel
import ru.skriplenok.shoppinglist.R
import ru.skriplenok.shoppinglist.adapters.CreatorAdapter
import ru.skriplenok.shoppinglist.models.ProductsModel

class CreatorViewModel: ViewModel(), ProductCellViewModel {

    val adapter: CreatorAdapter = CreatorAdapter(R.layout.product_cell, this)
    val itemCount: Int
        get() = productList.size

    private val productList: List<ProductsModel> = mutableListOf()

    fun init() {}

    fun setupAdapter() = adapter.notifyDataSetChanged()

    override fun getItem(position: Int): ProductsModel? {
        if(position < productList.size) {
            return productList[position]
        }
        return null
    }

    override fun getVisible(): Boolean = false
}