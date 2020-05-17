package ru.skriplenok.shoppinglist.viewmodel

import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.runBlocking
import ru.skriplenok.shoppinglist.R
import ru.skriplenok.shoppinglist.adapters.ProductsAdapter
import ru.skriplenok.shoppinglist.helpers.Convectors
import ru.skriplenok.shoppinglist.models.ProductsModel
import ru.skriplenok.shoppinglist.repositories.Converters
import ru.skriplenok.shoppinglist.repositories.ProductRepository
import ru.skriplenok.shoppinglist.repositories.dto.ProductDto

class ProductsViewModel(
    private val productRepository: ProductRepository,
    handle: SavedStateHandle
): ViewModel(), ProductCellViewModel {

    val adapter: ProductsAdapter = ProductsAdapter(R.layout.product_cell, this)
    val loading: ObservableInt = ObservableInt(View.GONE)

    private var productDtoList: List<ProductDto> = mutableListOf()
    private var productList: List<ProductsModel> = mutableListOf()

    fun init(shoppingId: Int) {
        runBlocking {
            productDtoList = productRepository.getByShoppingId(shoppingId)
            productList = Convectors.productDtoToProductModel(productDtoList)
        }
    }

    fun setModelInAdapter() = adapter.notifyDataSetChanged()

    override fun getItem(position: Int): ProductsModel? {
        if (position < productDtoList.size) {
            return productList[position]
        }
        return null
    }

    override fun getVisible(): Int = View.VISIBLE

    override fun itemCount(): Int =  productDtoList.size
}