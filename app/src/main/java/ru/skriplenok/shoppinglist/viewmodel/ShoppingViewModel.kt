package ru.skriplenok.shoppinglist.viewmodel

import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.runBlocking
import ru.skriplenok.shoppinglist.R
import ru.skriplenok.shoppinglist.adapters.ShoppingAdapter
import ru.skriplenok.shoppinglist.repositories.ShoppingRepository
import ru.skriplenok.shoppinglist.repositories.dto.ShoppingWithCount

class ShoppingViewModel(
    private val shoppingRepository: ShoppingRepository,
    handle: SavedStateHandle
): ViewModel() {

    val loading: ObservableInt = ObservableInt(View.GONE)
    val adapter: ShoppingAdapter = ShoppingAdapter(R.layout.shopping_cell, this)
    val countItems: Int
        get() = shoppingList.count()
    val selected: MutableLiveData<ShoppingWithCount> = MutableLiveData()
    val longSelected: MutableLiveData<ShoppingWithCount> = MutableLiveData()

    //TODO заменить на liveData
    private var shoppingList: List<ShoppingWithCount> = mutableListOf()

    fun init() {
        selected.value = null
        longSelected.value = null
        runBlocking {
            shoppingList = shoppingRepository.getAllActive()
        }
    }

    fun setModelInAdapter() = adapter.notifyDataSetChanged()

    fun getItem(position: Int): ShoppingWithCount? {
        if (position < shoppingList.size) {
            return shoppingList[position]
        }
        return null
    }

    fun getCount(position: Int): String? {
        if (position < shoppingList.size) {
            val shopping = shoppingList[position]
            return shopping.productsActive.toString() + "/" + shopping.productsAll.toString()
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