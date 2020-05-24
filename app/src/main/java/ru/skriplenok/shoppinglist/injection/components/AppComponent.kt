package ru.skriplenok.shoppinglist.injection.components

import dagger.Component
import ru.skriplenok.shoppinglist.injection.modules.RoomModule
import ru.skriplenok.shoppinglist.repositories.dao.ProductTypeDao
import ru.skriplenok.shoppinglist.ui.CreatorFragment
import ru.skriplenok.shoppinglist.ui.ProductsFragment
import ru.skriplenok.shoppinglist.ui.ShoppingFragment
import javax.inject.Singleton

@Singleton
@Component(dependencies = [], modules = [RoomModule::class])
interface AppComponent {

    fun getProductTypeDao(): ProductTypeDao

    fun inject(fragment: ShoppingFragment)

    fun inject(fragment: ProductsFragment)

    fun inject(fragment: CreatorFragment)
}