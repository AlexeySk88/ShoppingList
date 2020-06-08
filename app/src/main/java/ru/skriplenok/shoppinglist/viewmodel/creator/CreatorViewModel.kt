package ru.skriplenok.shoppinglist.viewmodel.creator

import android.content.Context
import android.view.View
import android.widget.ArrayAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.skriplenok.shoppinglist.R
import ru.skriplenok.shoppinglist.adapters.ProductsAdapter
import ru.skriplenok.shoppinglist.helpers.Constants
import ru.skriplenok.shoppinglist.helpers.Converters
import ru.skriplenok.shoppinglist.helpers.QuantityTypes
import ru.skriplenok.shoppinglist.helpers.StringHelper
import ru.skriplenok.shoppinglist.models.CreatorModel
import ru.skriplenok.shoppinglist.models.ShoppingIdWithTitle
import ru.skriplenok.shoppinglist.repositories.dto.ProductDto
import ru.skriplenok.shoppinglist.ui.toolbars.CreatorToolbar.ItemMenu
import ru.skriplenok.shoppinglist.viewmodel.ProductCellViewModel
import javax.inject.Inject

class CreatorViewModel @Inject constructor(
    shoppingIdWithTitle: ShoppingIdWithTitle?,
    toolbarMenuSelected: MutableLiveData<ItemMenu>,
    private val state: CreatorState
): ViewModel(), ProductCellViewModel {

    val adapter: ProductsAdapter = ProductsAdapter(R.layout.product_cell, this)
    var spinnerAdapter: ArrayAdapter<String>? = null
    val productsNumber: MutableLiveData<String> = MutableLiveData()
    val toastMessage: MutableLiveData<String> = MutableLiveData()
    val onClose: MutableLiveData<Boolean> = MutableLiveData()
    val creatorModel: MutableLiveData<CreatorModel> =
        MutableLiveData(CreatorModel("", mutableListOf(), "", "", 0))

    private var quantityTypes: QuantityTypes = QuantityTypes.getInstance()

    init {
        state.setTitleAndProductList(shoppingIdWithTitle, creatorModel.value!!)
        setProductsNumber()
        toolbarMenuSelected.observeForever {
            onClickListSave(it)
        }
    }

    private fun setProductsNumber() {
        if (itemCount() > 0 ) {
            productsNumber.value = "Товаров в списке: ${itemCount()}"
            return
        }
        productsNumber.value = "Добавьте в список хотя бы один товар"
    }

    private fun onClickListSave(itemMenu: ItemMenu?) {
        if (itemMenu != ItemMenu.SAVE) {
            return
        }
        if (!validateShopping()) {
            return
        }

        state.shoppingSave(creatorModel.value!!)
        onClose.value = true
    }

    private fun validateShopping(): Boolean {
        val sb = StringBuilder()
        if (creatorModel.value?.title?.isEmpty()!!) {
            sb.append(formatItemValidateMessage(Constants.NAME_SHOPPING.value))
        }
        if (itemCount() == 0) {
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
        return creatorModel.value?.productList?.get(position)?.product?.name
    }

    override fun getSelected(position: Int): Boolean {
        creatorModel.value?.let {
            if (position < itemCount()) {
                return it.productList[position].product.selectedDate != null
            }
        }
        return false
    }

    // Метод без реализации т.к. checkbox на который повесили событие скрыта
    override fun onSelected(position: Int) { }

    override fun getQuantity(position: Int): String? {
        creatorModel.value?.let {
            if (position < itemCount()) {
                val product = it.productList[position]
                return StringHelper.getQuantity(product.product.quantity, product.typeShortName)
            }
        }
        return null
    }

    override fun getVisible(): Int = View.GONE

    override fun itemCount(): Int {
        creatorModel.value?.let {
            return it.productList.size
        }
        return 0
    }

    fun onClickProductSave() {
        creatorModel.value?.let {
            if (!validateProduct(it)) {
                return
            }
            val type = quantityTypes.list[it.indexType]
            // Так как мы не знаем shoppingId, то сейчас всем товарам в списке задаём 0,
            // поэтому перед записью в БД обязательно прогоняем весь список через метод state.setShoppingID()
            // TODO убрать эту конвертацию
            val productDto = ProductDto(0, 0, type.id, it.productName, it.productQuantity)
            it.productList.add(Converters.productDtoToProductModel(productDto))
            adapter.notifyItemInserted(itemCount() - 1)
            setProductsNumber()

            it.productName = ""
            it.productQuantity = ""
        }
    }

    private fun validateProduct(model: CreatorModel): Boolean {
        val sb = StringBuilder()
        if (model.productName.isEmpty()) {
            sb.append(formatItemValidateMessage(Constants.NAME_PRODUCT.value))
        }
        if (model.productQuantity.isEmpty()) {
            sb.append(formatItemValidateMessage(Constants.COUNT_PRODUCT.value))
        }

        if (sb.isNotEmpty()) {
            sb.insert(0, Constants.CREATOR_NOT_VALIDATED.value + ":")
            toastMessage.value  = sb.toString()
            return false
        }
        return true
    }
}