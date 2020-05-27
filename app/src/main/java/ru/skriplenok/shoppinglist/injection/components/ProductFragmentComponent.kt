package ru.skriplenok.shoppinglist.injection.components

import dagger.Component
import ru.skriplenok.shoppinglist.injection.modules.ProductToolbarModule
import ru.skriplenok.shoppinglist.injection.modules.RoomModule
import ru.skriplenok.shoppinglist.ui.ProductsFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [ProductToolbarModule::class, RoomModule::class])
interface ProductFragmentComponent {

    fun inject(productsFragment: ProductsFragment)
}