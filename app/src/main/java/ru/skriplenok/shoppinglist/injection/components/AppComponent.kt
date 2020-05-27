package ru.skriplenok.shoppinglist.injection.components

import dagger.Component
import ru.skriplenok.shoppinglist.injection.modules.RoomModule
import ru.skriplenok.shoppinglist.repositories.dao.ProductTypeDao
import javax.inject.Singleton

@Singleton
@Component(dependencies = [], modules = [RoomModule::class])
interface AppComponent {

    fun getProductTypeDao(): ProductTypeDao
}