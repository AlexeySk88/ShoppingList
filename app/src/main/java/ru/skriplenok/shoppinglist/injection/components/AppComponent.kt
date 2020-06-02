package ru.skriplenok.shoppinglist.injection.components

import dagger.Component
import ru.skriplenok.shoppinglist.injection.modules.CreatorModule
import ru.skriplenok.shoppinglist.injection.modules.ProductModule
import ru.skriplenok.shoppinglist.injection.modules.RoomModule
import ru.skriplenok.shoppinglist.injection.modules.ShoppingModule
import ru.skriplenok.shoppinglist.repositories.dao.ProductTypeDao
import javax.inject.Singleton

@Singleton
@Component(dependencies = [], modules = [RoomModule::class])
interface AppComponent {

    fun getProductTypeDao(): ProductTypeDao

    fun shoppingComponent(shoppingModule: ShoppingModule): ShoppingComponent

    fun productComponent(productModule: ProductModule): ProductComponent

    fun creatorComponent(creatorModule: CreatorModule): CreatorComponent
}