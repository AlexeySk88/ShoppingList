package ru.skriplenok.shoppinglist.injection.modules

import dagger.Module
import dagger.Provides
import ru.skriplenok.shoppinglist.injection.scopes.ProductScope
import ru.skriplenok.shoppinglist.repositories.ProductRepository
import ru.skriplenok.shoppinglist.ui.toolbars.ProductToolbar
import ru.skriplenok.shoppinglist.ui.ProductsFragment
import ru.skriplenok.shoppinglist.viewmodel.ProductsViewModel

@Module
class ProductModule(
    private val productFragment: ProductsFragment
) {

    @Provides
    @ProductScope
    fun provideProductViewModel(productRepository: ProductRepository): ProductsViewModel {
        return ProductsViewModel(
            productRepository,
            productFragment.shoppingIdWithTitle.id
        )
    }

    @Provides
    @ProductScope
    fun provideProductToolbar(): ProductToolbar {
        return ProductToolbar(
            productFragment.toolbarView,
            productFragment.shoppingIdWithTitle.title,
            productFragment.toolbarMenuSelected
        )
    }
}