package ru.skriplenok.shoppinglist.viewmodel

import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.skriplenok.shoppinglist.R
import ru.skriplenok.shoppinglist.adapters.ShoppingAdapter
import ru.skriplenok.shoppinglist.helpers.StringHelper
import ru.skriplenok.shoppinglist.models.ShoppingModel
import ru.skriplenok.shoppinglist.repositories.ShoppingRepository
import ru.skriplenok.shoppinglist.services.ShoppingToolbar
import ru.skriplenok.shoppinglist.services.ShoppingToolbar.ItemMenu
import javax.inject.Inject

class ShoppingViewModel @Inject constructor(
    private val shoppingRepository: ShoppingRepository,
    private val longClickSelectedCount: MutableLiveData<Int>,
    private val itemSelected: MutableLiveData<ItemMenu>
): ViewModel() {

    val adapter: ShoppingAdapter = ShoppingAdapter(R.layout.shopping_cell, this)
    val clickSelected: MutableLiveData<ShoppingModel> = MutableLiveData()

    var showSideCheckBox: ObservableInt = ObservableInt(View.GONE)
    val countItems: Int
        get() = shoppingList.count()

    // id выбранных списков
    private val selectedIds: MutableSet<Int> = mutableSetOf()
    private var shoppingList: MutableList<ShoppingModel> = mutableListOf()

    fun init() {
        clickSelected.value = null
        itemSelected.observeForever {
            setMenuItemClickListener(it)
        }
        selectedIds.clear()
        runBlocking {
            shoppingList = shoppingRepository.getAllActive()
        }
        if (!shoppingList.isNullOrEmpty()) {
            validateShoppingList()
        }
    }

    private fun setMenuItemClickListener(itemMenu: ItemMenu?) {
        if (itemMenu === null) {
            return
        }
        if (itemMenu === ItemMenu.ARROW) {
            selectedIds.clear()
            setShowSideCheckBox(View.GONE)
            adapter.notifyDataSetChanged()
        }
    }

    private fun validateShoppingList() {
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
        if (longClickSelectedCount.value === null) {
            clickSelected.value = getItem(position)
        }
    }

    fun onLongClick(position: Int): Boolean {
        onChecked(position)
        return true
    }

    fun onChecked(position: Int) {
        setShowSideCheckBox(View.VISIBLE)
        setSelectedIds(position)
        longClickSelectedCount.value = selectedIds.size
        if (selectedIds.size == 0) {
            setShowSideCheckBox(View.GONE)
        }
    }

    private fun setShowSideCheckBox(view: Int) {
        if (showSideCheckBox.get() != view) {
            showSideCheckBox.set(view)
        }
    }

    private fun setSelectedIds(position: Int) {
        val shoppingModel = getItem(position)
        if(shoppingModel === null) {
            return
        }

        val shoppingId = shoppingModel.shopping.id
        if (selectedIds.contains(shoppingId)) {
            selectedIds.remove(shoppingId)
        } else {
            selectedIds.add(shoppingId)
        }
    }
}