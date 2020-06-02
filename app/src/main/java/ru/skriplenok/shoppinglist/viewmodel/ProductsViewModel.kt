package ru.skriplenok.shoppinglist.viewmodel

import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.skriplenok.shoppinglist.R
import ru.skriplenok.shoppinglist.adapters.ProductsAdapter
import ru.skriplenok.shoppinglist.helpers.StringHelper
import ru.skriplenok.shoppinglist.models.ProductModel
import ru.skriplenok.shoppinglist.repositories.ProductRepository
import java.util.*
import javax.inject.Inject

class ProductsViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    shoppingId: Int
): ViewModel(), ProductCellViewModel {

    val adapter: ProductsAdapter = ProductsAdapter(R.layout.product_cell, this)
    private var productList: MutableList<ProductModel> = mutableListOf()

    init{
        runBlocking {
            productList = productRepository.getByShoppingId(shoppingId)
        }
    }

    override fun getTitle(position: Int): String? {
        if (position < itemCount()) {
            return productList[position].product.name
        }
        return null
    }

    override fun getSelected(position: Int): Boolean {
        if (position< itemCount()) {
            return productList[position].product.selectedDate != null
        }
        return false
    }

    override fun onSelected(position: Int) {
        if (position >= itemCount()) {
            return
        }

        val selectedProduct = productList[position]
        setSelectedDate(selectedProduct)
        viewModelScope.launch {
            productRepository.update(selectedProduct)
        }
    }

    private fun setSelectedDate(model: ProductModel) {
        if (model.product.selectedDate == null) {
            model.product.selectedDate = Calendar.getInstance()
        } else {
            model.product.selectedDate = null
        }
    }

    override fun getQuantity(position: Int): String? {
        if (position < itemCount()) {
            val product = productList[position]
            return StringHelper.getQuantity(product.product.quantity, product.typeShortName)
        }
        return null
    }

    override fun getVisible(): Int = View.VISIBLE

    override fun itemCount() = productList.size
}