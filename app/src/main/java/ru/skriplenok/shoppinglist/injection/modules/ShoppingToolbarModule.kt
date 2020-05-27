package ru.skriplenok.shoppinglist.injection.modules

import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.Module
import dagger.Provides
import ru.skriplenok.shoppinglist.services.ShoppingToolbar
import ru.skriplenok.shoppinglist.services.ShoppingToolbar.ItemMenu
import javax.inject.Singleton

@Module
class ShoppingToolbarModule(
    private val toolbar: Toolbar,
    private val toolbarMenuSelected: MutableLiveData<ItemMenu>,
    private val count: LiveData<Int>
) {

    @Singleton
    @Provides
    fun provideShoppingToolbar(): ShoppingToolbar = ShoppingToolbar(toolbar, toolbarMenuSelected, count)
}