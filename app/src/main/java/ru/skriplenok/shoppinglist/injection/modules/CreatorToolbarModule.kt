package ru.skriplenok.shoppinglist.injection.modules

import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.MutableLiveData
import dagger.Module
import dagger.Provides
import ru.skriplenok.shoppinglist.services.CreatorToolbar
import ru.skriplenok.shoppinglist.services.CreatorToolbar.ItemMenu
import javax.inject.Singleton

@Module
class CreatorToolbarModule(
    private val toolbar: Toolbar,
    private val itemSelected: MutableLiveData<ItemMenu>
) {

    @Provides
    @Singleton
    fun provideCreatorToolbar(): CreatorToolbar = CreatorToolbar(toolbar, itemSelected)
}