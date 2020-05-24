package ru.skriplenok.shoppinglist.injection.modules

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.runBlocking
import ru.skriplenok.shoppinglist.repositories.ProductRepository
import ru.skriplenok.shoppinglist.repositories.RoomAppDatabase
import ru.skriplenok.shoppinglist.repositories.ShoppingRepository
import ru.skriplenok.shoppinglist.repositories.contracts.RoomContract
import ru.skriplenok.shoppinglist.repositories.dao.ProductDao
import ru.skriplenok.shoppinglist.repositories.dao.ProductTypeDao
import ru.skriplenok.shoppinglist.repositories.dao.ShoppingDao
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
class RoomModule(context: Context) {

    private val dataBase: RoomAppDatabase =
        Room.databaseBuilder(context, RoomAppDatabase::class.java, RoomContract.DATABASE_NAME)
            .addCallback(object: RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    Executors.newSingleThreadScheduledExecutor()
                        .execute {
                            RoomAppDatabase.apply {
                                provideShoppingDao().insertAll(shoppingList)
                                provideProductTypeDao().insertAll(typeList)

                                runBlocking {
                                    provideProductDao().insertAll(productList)
                                }
                            }
                        }
                }
            })
            .build()

    @Provides
    @Singleton
    fun provideRoomDataBase(): RoomAppDatabase = dataBase

    @Provides
    @Singleton
    fun provideShoppingDao(): ShoppingDao = dataBase.shoppingDao()

    @Provides
    @Singleton
    fun provideProductDao(): ProductDao = dataBase.productDao()

    @Provides
    @Singleton
    fun provideProductTypeDao(): ProductTypeDao = dataBase.productTypeDao()

    @Provides
    @Singleton
    fun provideShoppingRepository(): ShoppingRepository = ShoppingRepository(dataBase.shoppingDao())

    @Provides
    @Singleton
    fun provideProductRepository(): ProductRepository = ProductRepository(dataBase.productDao())
}