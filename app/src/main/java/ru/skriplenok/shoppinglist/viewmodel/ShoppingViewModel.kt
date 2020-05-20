package ru.skriplenok.shoppinglist.viewmodel

import android.view.Menu
import android.view.View
import androidx.appcompat.view.ActionMode
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
        set(value) {
            field = value
            if (value != null) {
                showSideCheckBox.set(View.VISIBLE)
            } else {
                // TODO очищать всё выбранные checkbox'ы при завершении ActionMode
                selectedIds.clear()
                showSideCheckBox.set(View.GONE)
            }
        }
    val clickSelected: MutableLiveData<ShoppingModel> = MutableLiveData()
    val longClickSelectedCount: MutableLiveData<Int> = MutableLiveData()

    var showSideCheckBox: ObservableInt = ObservableInt(View.GONE)
    val countItems: Int
        get() = shoppingList.count()

    // id выбранных списков
    private val selectedIds: MutableSet<Int> = mutableSetOf()
    private var shoppingList: MutableList<ShoppingModel> = mutableListOf()

    fun init() {
        clickSelected.value = null
        longClickSelectedCount.value = null
        selectedIds.clear()
        runBlocking {
            shoppingList = shoppingRepository.getAllActive()
        }
        if (!shoppingList.isNullOrEmpty()) {
            validateShoppingList()
        }
    }

    private fun validateShoppingList() {
        //TODO собирать на модели, а id
        val archiveList = mutableListOf<ShoppingModel>()
        val newShoppingList = mutableListOf<ShoppingModel>()
        for (shopping in shoppingList) {
            if (shopping.productsAll == shopping.productsActive) {
                archiveList.add(shopping)
                continue
            }
            newShoppingList.add(shopping)
        }
        shoppingList = newShoppingList
        viewModelScope.launch {
            shoppingRepository.updateAll(archiveList)
        }
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
        // пока не завершим ActionMode блокируем выбор списка товаров
        if (mActionMode === null) {
            clickSelected.value = getItem(position)
        }
    }

    fun onLongClick(position: Int): Boolean {
        onChecked(position)
        return true
    }

    fun onChecked(position: Int) {
        if (shoppingList.size <= position) {
            return
        }
        val shoppingId = getItem(position)!!.shopping.id
        if (selectedIds.contains(shoppingId)) {
            selectedIds.remove(shoppingId)
        } else {
            selectedIds.add(shoppingId)
        }
        longClickSelectedCount.value = selectedIds.size
        setMenuItemVisible()
    }

    private fun setMenuItemVisible() {
        val menu = mActionMode?.menu
        val edit = menu?.findItem(R.id.toolbarEdit)
        val share = menu?.findItem(R.id.toolbarShare)

        when(selectedIds.size) {
            1 -> { edit?.isVisible = true
                share?.isVisible = true }
            2 -> { edit?.isVisible = false
                share?.isVisible = false }
        }
    }
}