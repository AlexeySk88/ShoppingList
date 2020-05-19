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
        get() = shoppingList.value?.count() ?: 0
    val selected: MutableLiveData<ShoppingModel> = MutableLiveData()
    val longSelected: MutableLiveData<ShoppingModel> = MutableLiveData()

    private var shoppingList: LiveData<MutableList<ShoppingModel>> = MutableLiveData()

    fun init() {
        viewModelScope.launch {  }
        selected.value = null
        longSelected.value = null
        runBlocking {
            shoppingList = shoppingRepository.getAllActive()
        }
        if (!shoppingList.value.isNullOrEmpty()) {
            validateShoppingList()
        }
    }

    private fun validateShoppingList() {
        val archiveList = mutableListOf<ShoppingModel>()
        for (shopping in shoppingList.value!!) {
            if (shopping.productsAll == shopping.productsActive) {
                archiveList.add(shopping)
            }
        }
        viewModelScope.launch {
            shoppingRepository.updateAll(archiveList)
        }
        shoppingList.value!!.removeAll(archiveList)
        adapter.notifyDataSetChanged()
    }

    fun getItem(position: Int): ShoppingModel? {
        if (position < countItems) {
            return shoppingList.value?.get(position)
        }
        return null
    }

    fun getCount(position: Int): String? {
        if (position < countItems) {
            val shopping = shoppingList.value!![position]
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