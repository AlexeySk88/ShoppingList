package ru.skriplenok.shoppinglist.injection.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.skriplenok.shoppinglist.repositories.RoomAppDatabase
import ru.skriplenok.shoppinglist.repositories.contracts.RoomContract
import ru.skriplenok.shoppinglist.repositories.dao.ProductDao
import ru.skriplenok.shoppinglist.repositories.dao.ShoppingDao
import javax.inject.Singleton

@Module
class RoomModule(context: Context) {

    private val dataBase: RoomAppDatabase = Room.databaseBuilder(context,
                                                                 RoomAppDatabase::class.java,
                                                                 RoomContract.DATABASE_NAME).build()

    @Provides
    @Singleton
    fun provideRoomDataBase(): RoomAppDatabase = dataBase

    @Provides
    @Singleton
    fun provideShoppingDao(): ShoppingDao = dataBase.shoppingDao()

    @Provides
    @Singleton
    fun provideProductDao(): ProductDao = dataBase.productDao()
}