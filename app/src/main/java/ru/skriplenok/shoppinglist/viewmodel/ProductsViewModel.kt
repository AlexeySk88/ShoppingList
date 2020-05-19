package ru.skriplenok.shoppinglist.viewmodel

import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.runBlocking
import ru.skriplenok.shoppinglist.R
import ru.skriplenok.shoppinglist.adapters.ProductsAdapter
import ru.skriplenok.shoppinglist.helpers.StringHelper
import ru.skriplenok.shoppinglist.models.ProductModel
import ru.skriplenok.shoppinglist.repositories.ProductRepository

class ProductsViewModel(
    private val productRepository: ProductRepository,
    handle: SavedStateHandle
): ViewModel(), ProductCellViewModel {

    val adapter: ProductsAdapter = ProductsAdapter(R.layout.product_cell, this)
    val loading: ObservableInt = ObservableInt(View.GONE)

    private var productList: LiveData<MutableList<ProductModel>> = MutableLiveData()

    fun init(shoppingId: Int) {
        runBlocking {
            productList = productRepository.getByShoppingId(shoppingId)
        }
    }

    fun setModelInAdapter() = adapter.notifyDataSetChanged()

    override fun getTitle(position: Int): String? {
        if (position < itemCount()) {
            return productList.value!![position].product.name
        }
        return null
    }

    override fun getSelected(position: Int): Boolean {
        if (position< itemCount()) {
            return productList.value!![position].product.selectedDate != null
        }
        return false
    }

    override fun getQuantity(position: Int): String? {
        if (position < itemCount()) {
            val product = productList.value!![position]
            return StringHelper.getQuantity(product.product.quantity, product.typeShortName)
        }
        return null
    }

    override fun getVisible(): Int = View.VISIBLE

    override fun itemCount() = productList.value?.size ?: 0
}