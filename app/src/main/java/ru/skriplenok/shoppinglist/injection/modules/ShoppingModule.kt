package ru.skriplenok.shoppinglist.injection.modules

import dagger.Module
import dagger.Provides
import ru.skriplenok.shoppinglist.injection.scopes.ShoppingScope
import ru.skriplenok.shoppinglist.repositories.ShoppingRepository
import ru.skriplenok.shoppinglist.services.ShoppingToolbar
import ru.skriplenok.shoppinglist.ui.ShoppingFragment
import ru.skriplenok.shoppinglist.viewmodel.ShoppingViewModel

@Module
class ShoppingModule(
    private val shoppingFragment: ShoppingFragment
) {

    @Provides
    @ShoppingScope
    fun provideShoppingViewModel(shoppingRepository: ShoppingRepository): ShoppingViewModel {
        return ShoppingViewModel(
            shoppingRepository,
            shoppingFragment.longClickSelectedCount,
            shoppingFragment.toolbarMenuSelected
        )
    }

    @Provides
    @ShoppingScope
    fun provideShoppingToolbar(): ShoppingToolbar {
        return ShoppingToolbar(
            shoppingFragment.toolbarView,
            shoppingFragment.toolbarMenuSelected,
            shoppingFragment.longClickSelectedCount
        )
    }
}
