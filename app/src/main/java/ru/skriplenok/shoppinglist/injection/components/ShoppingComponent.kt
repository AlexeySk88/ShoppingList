package ru.skriplenok.shoppinglist.injection.components

import dagger.Subcomponent
import ru.skriplenok.shoppinglist.injection.modules.ShoppingModule
import ru.skriplenok.shoppinglist.injection.scopes.ShoppingScope
import ru.skriplenok.shoppinglist.ui.ShoppingFragment

@ShoppingScope
@Subcomponent(modules = [ShoppingModule::class])
interface ShoppingComponent {

    fun inject(shoppingFragment: ShoppingFragment)
}