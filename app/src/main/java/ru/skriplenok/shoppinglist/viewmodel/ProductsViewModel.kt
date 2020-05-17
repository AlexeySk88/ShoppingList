package ru.skriplenok.shoppinglist.viewmodel

import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.runBlocking
import ru.skriplenok.shoppinglist.R
import ru.skriplenok.shoppinglist.adapters.ProductsAdapter
import ru.skriplenok.shoppinglist.helpers.Converters
import ru.skriplenok.shoppinglist.helpers.StringHelper
import ru.skriplenok.shoppinglist.models.ProductModel
import ru.skriplenok.shoppinglist.repositories.ProductRepository
import ru.skriplenok.shoppinglist.repositories.dto.ProductDto

class ProductsViewModel(
    private val productRepository: ProductRepository,
    handle: SavedStateHandle
): ViewModel(), ProductCellViewModel {

    val adapter: ProductsAdapter = ProductsAdapter(R.layout.product_cell, this)
    val loading: ObservableInt = ObservableInt(View.GONE)

    private var productDtoList: List<ProductDto> = mutableListOf()
    private var productList: List<ProductModel> = mutableListOf()

    fun init(shoppingId: Int) {
        runBlocking {
            productDtoList = productRepository.getByShoppingId(shoppingId)
            productList = Converters.productDtoToProductModel(productDtoList)
        }
    }

    fun setModelInAdapter() = adapter.notifyDataSetChanged()

    override fun getItem(position: Int): ProductModel? {
        if (position < productDtoList.size) {
            return productList[position]
        }
        return null
    }

    override fun getQuantity(position: Int): String? {
        if (position < productDtoList.size) {
            val product = productList[position]
            return StringHelper.getQuantity(product.quantity, product.type)
        }
        return null
    }

    override fun getVisible(): Int = View.VISIBLE

    override fun itemCount(): Int =  productDtoList.size
}