package ru.skriplenok.shoppinglist.viewmodel

import android.content.Context
import android.view.View
import android.widget.ArrayAdapter
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.runBlocking
import ru.skriplenok.shoppinglist.R
import ru.skriplenok.shoppinglist.adapters.ProductsAdapter
import ru.skriplenok.shoppinglist.helpers.Constants
import ru.skriplenok.shoppinglist.helpers.Converters
import ru.skriplenok.shoppinglist.helpers.QuantityTypes
import ru.skriplenok.shoppinglist.helpers.StringHelper
import ru.skriplenok.shoppinglist.models.ProductModel
import ru.skriplenok.shoppinglist.models.ShoppingIdWithTitle
import ru.skriplenok.shoppinglist.repositories.ProductRepository
import ru.skriplenok.shoppinglist.repositories.ShoppingRepository
import ru.skriplenok.shoppinglist.repositories.dto.ProductDto
import ru.skriplenok.shoppinglist.repositories.dto.ShoppingDto
import ru.skriplenok.shoppinglist.services.CreatorToolbar.ItemMenu
import javax.inject.Inject

class CreatorViewModel @Inject constructor(
    private val shoppingRepository: ShoppingRepository,
    private val productRepository: ProductRepository,
    private val shoppingIdWithTitle: ShoppingIdWithTitle?,
    toolbarMenuSelected: MutableLiveData<ItemMenu>
): ViewModel(), ProductCellViewModel {

    val adapter: ProductsAdapter = ProductsAdapter(R.layout.product_cell, this)
    var spinnerAdapter: ArrayAdapter<String>? = null

    val title: ObservableField<String> = ObservableField()
    val productsNumber: ObservableField<String> = ObservableField()
        get() {
            if (productList.size > 0 ) {
                field.set("Товаров в списке: " + productList.size)
            } else {
                field.set("Добавьте в список хотя бы один товар")
            }
            return field
        }
    val name: ObservableField<String> = ObservableField()
    val count: ObservableField<String> = ObservableField()
    val indexType: ObservableInt = ObservableInt()

    val toastMessage: MutableLiveData<String> = MutableLiveData()
    val onClose: MutableLiveData<Boolean> = MutableLiveData()

    private var quantityTypes: QuantityTypes = QuantityTypes.getInstance()
    private val productList: MutableList<ProductModel> = mutableListOf()

    init {
        setTitleAndProductList()
        setProductsNumber()
        toolbarMenuSelected.observeForever {
            onClickShoppingSave(it)
        }
    }

    private fun setTitleAndProductList() {
        if (shoppingIdWithTitle != null) {
            title.set(shoppingIdWithTitle.title)
            runBlocking {
                productList.addAll(productRepository.getByShoppingId(shoppingIdWithTitle.id))
            }
        }
    }

    private fun setProductsNumber() {
        if (productList.size > 0 ) {
            productsNumber.set("Товаров в списке: " + productList.size)
            return
        }
        productsNumber.set("Добавьте в список хотя бы один товар")
    }

    private fun onClickShoppingSave(itemMenu: ItemMenu?) {
        if (itemMenu != ItemMenu.SAVE) {
            return
        }
        if (!validateShopping()) {
            return
        }

        runBlocking {
            val shoppingDto = ShoppingDto(name = title.get()!!)
            val shoppingId = shoppingRepository.insert(shoppingDto)
            setShoppingId(shoppingId)
            productRepository.insertAll(productList)
        }
        onClose.value = true
    }

    private fun validateShopping(): Boolean {
        val sb = StringBuilder()
        if (title.get().isNullOrEmpty()) {
            sb.append(formatItemValidateMessage(Constants.NAME_SHOPPING.value))
        }
        if (productList.size == 0) {
            sb.append(formatItemValidateMessage(Constants.PRODUCT_COUNT.value))
        }

        if (sb.isNotEmpty()) {
            sb.insert(0, Constants.CREATOR_NOT_VALIDATED.value + ":")
            toastMessage.value = sb.toString()
            return false
        }
        return true
    }

    private fun formatItemValidateMessage(item: String) = "\n\t• $item"

    fun setSpinnerAdapter(context: Context) {
        val spinnerTypes: MutableList<String> = mutableListOf()
        for(quantityType in quantityTypes.list) {
            spinnerTypes.add(quantityType.fullName)
        }
        spinnerAdapter = ArrayAdapter(context, R.layout.spinner_item, spinnerTypes)
        spinnerAdapter?.setDropDownViewResource(R.layout.spinner_item)
    }

    override fun getTitle(position: Int): String? {
        if (position < productList.size) {
            return productList[position].product.name
        }
        return null
    }

    override fun getSelected(position: Int): Boolean {
        if (position < productList.size) {
            return productList[position].product.selectedDate != null
        }
        return false
    }

    // Метод без реализации т.к. checkbox на который повесили событие скрыта
    override fun onSelected(position: Int) { }

    override fun getQuantity(position: Int): String? {
        if (position < productList.size) {
            val product = productList[position]
            return StringHelper.getQuantity(product.product.quantity, product.typeShortName)
        }
        return null
    }

    override fun getVisible(): Int = View.GONE

    override fun itemCount(): Int = productList.size

    fun onClickSave() {
        if (!validateProduct()) {
            return
        }
        val type = quantityTypes.list[indexType.get()]
        // Так как мы не знаем shoppingId, то сейчас всем товарам в списке задаём 0,
        // поэтому перед записью в БД обязательно прогоняем весь список через метод setShoppingID()
        val productDto = ProductDto(0, 0, type.id, name.get()!!, count.get()!!)
        productList.add(Converters.productDtoToProductModel(productDto))
        adapter.notifyItemInserted(itemCount() - 1)
        setProductsNumber()

        name.set(null)
        count.set(null)
    }

    private fun validateProduct(): Boolean {
        val sb = StringBuilder()
        if (name.get().isNullOrEmpty()) {
            sb.append(formatItemValidateMessage(Constants.NAME_PRODUCT.value))
        }
        if (count.get().isNullOrEmpty()) {
            sb.append(formatItemValidateMessage(Constants.COUNT_PRODUCT.value))
        }

        if (sb.isNotEmpty()) {
            sb.insert(0, Constants.CREATOR_NOT_VALIDATED.value + ":")
            toastMessage.value  = sb.toString()
            return false
        }
        return true
    }

    private fun setShoppingId(shoppingId: Int) {
        for (item in productList) {
            item.product.shoppingId = shoppingId
        }
    }
}