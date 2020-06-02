package ru.skriplenok.shoppinglist.injection.components

import dagger.Subcomponent
import ru.skriplenok.shoppinglist.injection.modules.ProductModule
import ru.skriplenok.shoppinglist.injection.scopes.ProductScope
import ru.skriplenok.shoppinglist.ui.ProductsFragment

@ProductScope
@Subcomponent(modules = [ProductModule::class])
interface ProductComponent {

    fun inject(productFragment: ProductsFragment)
}