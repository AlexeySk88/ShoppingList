package ru.skriplenok.shoppinglist.services

import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.MutableLiveData
import ru.skriplenok.shoppinglist.R
import javax.inject.Inject

class CreatorToolbar @Inject constructor(
    toolbar: Toolbar,
    private val itemSelected: MutableLiveData<ItemMenu>
) {

    init {
        itemSelected.value = null
        toolbar.apply {
            inflateMenu(R.menu.creator_menu)
            title = resources.getString(R.string.creator_title)
            setOnMenuItemClickListener {
                menuItemClickListener(it)
            }
        }
    }

    private fun menuItemClickListener(itemClick: MenuItem):Boolean {
        for (item in ItemMenu.values()) {
            if (item.id == itemClick.itemId) {
                itemSelected.value = item
                break
            }
        }
        return false
    }

    enum class ItemMenu(val id: Int) {
        SAVE(R.id.toolbarSave),
        CLOSE(R.id.toolbarClose)
    }
}