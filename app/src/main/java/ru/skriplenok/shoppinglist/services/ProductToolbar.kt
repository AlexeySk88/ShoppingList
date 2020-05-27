package ru.skriplenok.shoppinglist.services

import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.MutableLiveData
import ru.skriplenok.shoppinglist.R
import javax.inject.Inject

class ProductToolbar @Inject constructor(
    toolbar: Toolbar,
    toolbarTitle: String?,
    private val itemSelected: MutableLiveData<ItemMenu>
    ) {

    init {
        toolbar.apply {
            title = toolbarTitle
            setNavigationIcon(R.drawable.ic_arrow_24dp)
            setNavigationOnClickListener {
                navigationClickListener()
            }
        }
    }

    private fun navigationClickListener() {
        itemSelected.value = ItemMenu.ARROW
    }

    enum class ItemMenu(val id: Int) {
        ARROW(-1)
    }
}