package ru.skriplenok.shoppinglist.viewmodel

import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.skriplenok.shoppinglist.R
import ru.skriplenok.shoppinglist.adapters.ShoppingAdapter
import ru.skriplenok.shoppinglist.models.ShoppingModel
import ru.skriplenok.shoppinglist.repositories.FakeRepositories
import ru.skriplenok.shoppinglist.repositories.Repository

class ShoppingViewModel: ViewModel() {

    val loading: ObservableInt = ObservableInt(View.GONE)
    val adapter: ShoppingAdapter = ShoppingAdapter(R.layout.shopping_cell, this)
    val countItems: Int
        get() = shoppingList.count()
    val selected: MutableLiveData<ShoppingModel> = MutableLiveData()
    val longSelected: MutableLiveData<ShoppingModel> = MutableLiveData()

    private val repository: Repository = FakeRepositories()
    private var shoppingList: List<ShoppingModel> = mutableListOf()

    fun init() {
        selected.value = null
        longSelected.value = null
    }

    fun fetchData(): List<ShoppingModel> { //TODO заменить на liveData
        shoppingList = repository.fetchShoppingList()
        return shoppingList
    }

    fun setModelInAdapter() {
        adapter.notifyDataSetChanged()
    }

    fun getItem(position: Int): ShoppingModel? {
        if (position < shoppingList.size) {
            return shoppingList[position]
        }
        return null
    }

    fun onClick(position: Int) {
        selected.value = getItem(position)
    }

    fun onLongClick(position: Int): Boolean {
        longSelected.value = getItem(position)
        return true
    }
}