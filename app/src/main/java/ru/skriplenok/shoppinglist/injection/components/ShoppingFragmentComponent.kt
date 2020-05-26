package ru.skriplenok.shoppinglist.injection.components

import dagger.Component
import ru.skriplenok.shoppinglist.injection.modules.RoomModule
import ru.skriplenok.shoppinglist.injection.modules.ShoppingToolbarModule
import ru.skriplenok.shoppinglist.injection.modules.ShoppingViewModelModule
import ru.skriplenok.shoppinglist.ui.ShoppingFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [ShoppingViewModelModule::class, ShoppingToolbarModule::class, RoomModule::class])
interface ShoppingFragmentComponent {

    fun inject(shoppingFragment: ShoppingFragment)
}