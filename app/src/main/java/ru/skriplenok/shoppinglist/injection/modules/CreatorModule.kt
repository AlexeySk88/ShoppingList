package ru.skriplenok.shoppinglist.injection.modules

import dagger.Module
import dagger.Provides
import ru.skriplenok.shoppinglist.injection.scopes.CreatorScope
import ru.skriplenok.shoppinglist.repositories.ProductRepository
import ru.skriplenok.shoppinglist.repositories.ShoppingRepository
import ru.skriplenok.shoppinglist.ui.CreatorFragment
import ru.skriplenok.shoppinglist.ui.toolbars.CreatorToolbar
import ru.skriplenok.shoppinglist.viewmodel.creator.ChangeState
import ru.skriplenok.shoppinglist.viewmodel.creator.CreatorState
import ru.skriplenok.shoppinglist.viewmodel.creator.CreatorViewModel
import ru.skriplenok.shoppinglist.viewmodel.creator.NewCreatorState

@Module
class CreatorModule(
    private val creatorFragment: CreatorFragment
) {

    @CreatorScope
    @Provides
    fun provideCreatorState(
        shoppingRepo: ShoppingRepository,
        productRepo: ProductRepository
    ): CreatorState = if (creatorFragment.shoppingIdWithTitle === null) {
            NewCreatorState(shoppingRepo, productRepo)
        } else {
            ChangeState(shoppingRepo, productRepo)
    }

    @CreatorScope
    @Provides
    fun provideCreatorViewModel(state: CreatorState): CreatorViewModel {
        return CreatorViewModel(
            creatorFragment.shoppingIdWithTitle,
            creatorFragment.toolbarMenuSelected,
            state
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