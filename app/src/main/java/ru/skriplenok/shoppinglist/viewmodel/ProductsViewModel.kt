package ru.skriplenok.shoppinglist.viewmodel

import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import ru.skriplenok.shoppinglist.R
import ru.skriplenok.shoppinglist.adapters.ProductsAdapter
import ru.skriplenok.shoppinglist.models.ProductsModel
import ru.skriplenok.shoppinglist.repositories.FakeRepositories
import ru.skriplenok.shoppinglist.repositories.Repository

class ProductsViewModel: ViewModel(), ProductCellViewModel {

    val adapter: ProductsAdapter = ProductsAdapter(R.layout.product_cell, this)
    val loading: ObservableInt = ObservableInt(View.GONE)

    private val repository: Repository = FakeRepositories()
    private var productList: List<ProductsModel> = mutableListOf()

    fun init() {}

    fun fetchData(id: Int): List<ProductsModel> {
        productList = repository.fetchProductList(id)
        return productList
    }

    fun setModelInAdapter() = adapter.notifyDataSetChanged()

    override fun getItem(position: Int): ProductsModel? {
        if (position < productList.size) {
            return productList[position]
        }
        return null
    }

    override fun getVisible(): Int = View.VISIBLE

    override fun itemCount(): Int =  productList.size
}