package ru.skriplenok.shoppinglist.viewmodel

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.skriplenok.shoppinglist.R
import ru.skriplenok.shoppinglist.adapters.ShoppingAdapter
import ru.skriplenok.shoppinglist.helpers.Constants
import ru.skriplenok.shoppinglist.helpers.StringHelper
import ru.skriplenok.shoppinglist.models.ShoppingIdWithTitle
import ru.skriplenok.shoppinglist.models.ShoppingModel
import ru.skriplenok.shoppinglist.repositories.ShoppingRepository
import ru.skriplenok.shoppinglist.services.ShoppingToolbar.ItemMenu
import javax.inject.Inject

class ShoppingViewModel @Inject constructor(
    private val shoppingRepository: ShoppingRepository,
    private val longClickSelectedCount: MutableLiveData<Int>,
    toolbarMenuSelected: MutableLiveData<ItemMenu>
): ViewModel() {

    val adapter: ShoppingAdapter = ShoppingAdapter(R.layout.shopping_cell, this)
    var showSideCheckBox: ObservableInt = ObservableInt(View.GONE)
    val countItems: Int
        get() = shoppingList.count()
    val navigation: MutableLiveData<Pair<Int, Bundle?>> = MutableLiveData()
    private var shoppingList: MutableList<ShoppingModel> = mutableListOf()
    // id выбранных списков
    private val itemsSelected: MutableSet<ShoppingModel> = mutableSetOf()

    init {
        navigation.value = null
        longClickSelectedCount.value = null
        toolbarMenuSelected.observeForever {
            setMenuItemClickListener(it)
        }
        itemsSelected.clear()
        runBlocking {
            shoppingList = shoppingRepository.getAllActive()
        }
        if (!shoppingList.isNullOrEmpty()) {
            validateShoppingList()
        }
    }

    private fun setMenuItemClickListener(itemMenu: ItemMenu?) {
        when (itemMenu) {
            ItemMenu.ARROW -> arrowClickHandler()
            ItemMenu.ADD  -> navigation.value = Pair(R.id.creatorFragment, null)
            ItemMenu.EDIT -> editClickHandler()
            ItemMenu.DELETE -> deleteClickHandler()
        }
    }

    private fun arrowClickHandler() {
        itemsSelected.clear()
        setShowSideCheckBox(View.GONE)
        adapter.notifyDataSetChanged()
        longClickSelectedCount.value = 0
    }

    private fun editClickHandler() {
        val bundle = bundleOf(
            Constants.SHOPPING_ID_WITH_TITLE.value to itemsSelected.first()
        )
        navigation.value = Pair(R.id.creatorFragment, bundle)
    }

    private fun deleteClickHandler() {
        viewModelScope.launch {
            shoppingRepository.deleteAll(itemsSelected.toList())
        }
        shoppingList.removeAll(itemsSelected)
        arrowClickHandler()
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
            val shoppingModel = getItem(position)
            shoppingModel?.let {
                val bundle = bundleOf(
                    Constants.SHOPPING_ID_WITH_TITLE.value to ShoppingIdWithTitle(it.shopping.id, it.shopping.name)
                )
                navigation.value = Pair(R.id.productsFragment, bundle)
            }
        }
    }

    fun onLongClick(position: Int): Boolean {
        onChecked(position)
        return true
    }

    fun onChecked(position: Int) {
        setShowSideCheckBox(View.VISIBLE)
        setSelectedIds(position)
        longClickSelectedCount.value = itemsSelected.size
        if (itemsSelected.size == 0) {
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
        shoppingModel?.let {
            if (itemsSelected.contains(it)) {
                itemsSelected.remove(it)
            } else {
                itemsSelected.add(it)
            }
        }
    }
}