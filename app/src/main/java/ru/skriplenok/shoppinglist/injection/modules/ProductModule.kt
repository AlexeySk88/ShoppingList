package ru.skriplenok.shoppinglist.injection.modules

import dagger.Module
import dagger.Provides
import ru.skriplenok.shoppinglist.injection.scopes.ProductScope
import ru.skriplenok.shoppinglist.services.ProductToolbar
import ru.skriplenok.shoppinglist.ui.ProductsFragment
import javax.inject.Singleton

@Module
class ProductModule(
    private val productFragment: ProductsFragment
) {

    @Provides
    @ProductScope
    fun provideProductToolbar(): ProductToolbar {
        return ProductToolbar(
            productFragment.toolbarView,
            productFragment.shoppingTitle,
            productFragment.toolbarMenuSelected
        )
    }
}