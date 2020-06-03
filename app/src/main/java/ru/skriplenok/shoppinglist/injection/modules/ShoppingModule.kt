package ru.skriplenok.shoppinglist.injection.modules

import androidx.lifecycle.MutableLiveData
import dagger.Module
import dagger.Provides
import ru.skriplenok.shoppinglist.injection.scopes.ShoppingScope
import ru.skriplenok.shoppinglist.repositories.ShoppingRepository
import ru.skriplenok.shoppinglist.ui.toolbars.ShoppingToolbar
import ru.skriplenok.shoppinglist.ui.ShoppingFragment
import ru.skriplenok.shoppinglist.viewmodel.ShoppingViewModel

@Module
class ShoppingModule(
    private val shoppingFragment: ShoppingFragment
) {

    private val toolbarMenuSelected: MutableLiveData<ShoppingToolbar.ItemMenu> = MutableLiveData()
    private val longClickSelectedCount: MutableLiveData<Int> = MutableLiveData()

    @Provides
    @ShoppingScope
    fun provideShoppingViewModel(shoppingRepository: ShoppingRepository): ShoppingViewModel {
        return ShoppingViewModel(
            shoppingRepository,
            longClickSelectedCount,
            toolbarMenuSelected
        )
    }

    @Provides
    @ShoppingScope
    fun provideShoppingToolbar(): ShoppingToolbar {
        return ShoppingToolbar(
            shoppingFragment.toolbarView,
            toolbarMenuSelected,
            longClickSelectedCount
        )
    }
}
