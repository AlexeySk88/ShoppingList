package ru.skriplenok.shoppinglist.viewmodel

import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import ru.skriplenok.shoppinglist.R
import ru.skriplenok.shoppinglist.adapters.ShoppingAdapter
import ru.skriplenok.shoppinglist.models.ShoppingModel
import ru.skriplenok.shoppinglist.repositories.FakeRepositories
import ru.skriplenok.shoppinglist.repositories.Repository

class ShoppingViewModel: ViewModel() {

    val loading: ObservableInt = ObservableInt(View.GONE)
    val adapter: ShoppingAdapter = ShoppingAdapter(R.layout.shopping_cell, this)

    private val repository: Repository = FakeRepositories()
    private var shoppingList: List<ShoppingModel> = mutableListOf()

    fun init() {

    }

    fun getList(): List<ShoppingModel> { //TODO заменить на liveData
        shoppingList = repository.fetchShoppingList()
        return shoppingList
    }

    fun setModelInAdapter(list: List<ShoppingModel>) {
        adapter.mDataList = list
        adapter.notifyDataSetChanged()
    }

    fun getItem(position: Int): ShoppingModel? {
        if (position < shoppingList.size) {
            return shoppingList[position]
        }

        return null
    }
}