package ru.skriplenok.shoppinglist.services

import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.skriplenok.shoppinglist.R
import javax.inject.Inject


class ShoppingToolbar @Inject constructor(
    private val toolbar: Toolbar,
    private val toolbarMenuSelected: MutableLiveData<ItemMenu>,
    count: LiveData<Int>
) {

    private var isActionMode: Boolean = false

    init {
        toolbarMenuSelected.value = null
        toolbar.apply {
            setOnMenuItemClickListener {
                menuItemClickListener(it)
            }
            setNavigationOnClickListener {
                navigationClickListener()
            }
        }
        setDefaultMode()
        count.observeForever {
            updateSelected(it)
        }
    }

    private fun menuItemClickListener(itemClick: MenuItem):Boolean {
        for (item in ItemMenu.values()) {
            if (item.id == itemClick.itemId) {
                toolbarMenuSelected.value = item
                break
            }
        }
        return false
    }

    private fun navigationClickListener() {
        setDefaultMode()
        isActionMode = false
        toolbarMenuSelected.value = ItemMenu.ARROW
    }

    private fun setDefaultMode() {
        toolbar.apply {
            menu.clear()
            inflateMenu(R.menu.shopping_menu)
            title = resources.getString(R.string.shopping_default_title)
            navigationIcon = null
        }
    }

    private fun setActionMode(count: Int) {
        toolbar.apply {
            menu.clear()
            inflateMenu(R.menu.shopping_menu_long_click)
            title = resources.getString(R.string.shopping_action_mode_title) + " " + count.toString()
            setNavigationIcon(R.drawable.ic_arrow_24dp)
        }
    }

    private fun updateSelected(count: Int?) {
        if (count == null) {
            return
        }
        if (count == 0) {
            isActionMode = false
            setDefaultMode()
        }
        if (!isActionMode and (count > 0)) {
            isActionMode = true
            setActionMode(count)
        }
        if (count > 0) {
            toolbar.title = toolbar.resources.getString(R.string.shopping_action_mode_title) + " " + count.toString()
            setVisibleItemMenu(count)
        }
    }

    private fun setVisibleItemMenu(count: Int) {
        val edit = toolbar.menu?.findItem(R.id.toolbarEdit)
        val share = toolbar.menu?.findItem(R.id.toolbarShare)

        when(count) {
            1 -> { edit?.isVisible = true
                share?.isVisible = true }
            2 -> { edit?.isVisible = false
                share?.isVisible = false }
        }
    }

    enum class ItemMenu(val id: Int) {
        ARROW(-1),
        ADD(R.id.toolbarAdd),
        SEARCH(R.id.toolbarSearch),
        EDIT(R.id.toolbarEdit),
        DELETE(R.id.toolbarDelete),
        SHARE(R.id.toolbarShare)
    }
}