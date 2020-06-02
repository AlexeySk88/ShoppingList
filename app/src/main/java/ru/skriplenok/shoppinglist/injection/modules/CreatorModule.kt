package ru.skriplenok.shoppinglist.injection.modules

import dagger.Module
import dagger.Provides
import ru.skriplenok.shoppinglist.injection.scopes.CreatorScope
import ru.skriplenok.shoppinglist.repositories.ProductRepository
import ru.skriplenok.shoppinglist.repositories.ShoppingRepository
import ru.skriplenok.shoppinglist.services.CreatorToolbar
import ru.skriplenok.shoppinglist.ui.CreatorFragment
import ru.skriplenok.shoppinglist.viewmodel.CreatorViewModel

@Module
class CreatorModule(
    private val creatorFragment: CreatorFragment
) {


    @CreatorScope
    @Provides
    fun provideCreatorViewModel(
        shoppingRepo: ShoppingRepository,
        productRepo: ProductRepository
    ): CreatorViewModel {
        return CreatorViewModel(
            shoppingRepo,
            productRepo,
            creatorFragment.shoppingIdWithTitle,
            creatorFragment.toolbarMenuSelected
        )
    }

    @CreatorScope
    @Provides
    fun provideCreatorToolbar(): CreatorToolbar {
        return CreatorToolbar(
            creatorFragment.toolbarView,
            creatorFragment.toolbarMenuSelected
        )
    }
}