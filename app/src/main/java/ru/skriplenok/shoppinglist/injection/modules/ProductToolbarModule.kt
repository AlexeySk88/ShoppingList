package ru.skriplenok.shoppinglist.injection.modules

import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.MutableLiveData
import dagger.Module
import dagger.Provides
import ru.skriplenok.shoppinglist.services.ProductToolbar
import ru.skriplenok.shoppinglist.services.ProductToolbar.ItemMenu
import javax.inject.Singleton

@Module
class ProductToolbarModule(
    private val toolbar: Toolbar,
    private val title: String?,
    private val itemSelected: MutableLiveData<ItemMenu>
) {

    @Provides
    @Singleton
    fun provideProductToolbar(): ProductToolbar = ProductToolbar(toolbar, title, itemSelected)
}