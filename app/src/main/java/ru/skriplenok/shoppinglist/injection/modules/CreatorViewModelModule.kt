package ru.skriplenok.shoppinglist.injection.modules

import androidx.lifecycle.MutableLiveData
import dagger.Module
import dagger.Provides
import ru.skriplenok.shoppinglist.repositories.ProductRepository
import ru.skriplenok.shoppinglist.repositories.ShoppingRepository
import ru.skriplenok.shoppinglist.services.CreatorToolbar.ItemMenu
import ru.skriplenok.shoppinglist.viewmodel.CreatorViewModel
import javax.inject.Singleton

@Module
class CreatorViewModelModule(
    private val toolbarMenuSelected: MutableLiveData<ItemMenu>
) {

    @Provides
    @Singleton
    fun provideCreatorViewModel(
        shoppingRepo: ShoppingRepository,
        productRepo: ProductRepository
    ): CreatorViewModel {
        return CreatorViewModel(shoppingRepo, productRepo, toolbarMenuSelected)
    }
}