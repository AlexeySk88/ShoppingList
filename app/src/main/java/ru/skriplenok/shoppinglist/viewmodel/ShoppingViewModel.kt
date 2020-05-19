package ru.skriplenok.shoppinglist.viewmodel

import androidx.appcompat.view.ActionMode
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.skriplenok.shoppinglist.R
import ru.skriplenok.shoppinglist.adapters.ShoppingAdapter
import ru.skriplenok.shoppinglist.helpers.StringHelper
import ru.skriplenok.shoppinglist.models.ShoppingModel
import ru.skriplenok.shoppinglist.repositories.ShoppingRepository

class ShoppingViewModel(
    private val shoppingRepository: ShoppingRepository,
    handle: SavedStateHandle
): ViewModel() {

    val adapter: ShoppingAdapter = ShoppingAdapter(R.layout.shopping_cell, this)
    var mActionMode: ActionMode? = null
    val countItems: Int
        get() = shoppingList.count()
    val selected: MutableLiveData<ShoppingModel> = MutableLiveData()
    val longSelected: MutableLiveData<ShoppingModel> = MutableLiveData()

    private var shoppingList: MutableList<ShoppingModel> = mutableListOf()

    fun init() {
        viewModelScope.launch {  }
        selected.value = null
        longSelected.value = null
        runBlocking {
            shoppingList = shoppingRepository.getAllActive()
        }
        if (!shoppingList.isNullOrEmpty()) {
            validateShoppingList()
        }
    }

    private fun validateShoppingList() {
        val archiveList = mutableListOf<ShoppingModel>()
        for (shopping in shoppingList) {
            if (shopping.productsAll == shopping.productsActive) {
                archiveList.add(shopping)
            }
        }
        viewModelScope.launch {
            shoppingRepository.updateAll(archiveList)
        }
        shoppingList.removeAll(archiveList)
        adapter.notifyDataSetChanged()
    }

    fun getItem(position: Int): ShoppingModel? {
        if (position < countItems) {
            return shoppingList[position]
        }
        return null
    }

    fun getCount(position: Int): String? {
        if (position < countItems) {
            val shopping = shoppingList[position]
            return StringHelper.getShoppingFraction(shopping.productsActive.toString(),
                                                    shopping.productsAll.toString())
        }
        return null
    }

    fun onClick(position: Int) {
        mActionMode?.finish()
        selected.value = getItem(position)
    }

    fun onLongClick(position: Int): Boolean {
        longSelected.value = getItem(position)
        return true
    }
}