package ru.skriplenok.shoppinglist.injection.modules

import androidx.lifecycle.MutableLiveData
import dagger.Module
import dagger.Provides
import ru.skriplenok.shoppinglist.repositories.ShoppingRepository
import ru.skriplenok.shoppinglist.services.ShoppingToolbar
import ru.skriplenok.shoppinglist.viewmodel.ShoppingViewModel
import javax.inject.Singleton

@Module
class ShoppingViewModelModule(
    private val longClickSelectedCount: MutableLiveData<Int>,
    private val itemSelected: MutableLiveData<ShoppingToolbar.ItemMenu>
) {

    @Provides
    @Singleton
    fun provideShoppingViewModel(shoppingRepository: ShoppingRepository): ShoppingViewModel {
        return ShoppingViewModel(shoppingRepository, longClickSelectedCount, itemSelected)
    }
}