package ru.skriplenok.shoppinglist.repositories

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.skriplenok.shoppinglist.repositories.contracts.RoomContract
import ru.skriplenok.shoppinglist.repositories.dao.ProductDao
import ru.skriplenok.shoppinglist.repositories.dao.ShoppingDao
import ru.skriplenok.shoppinglist.repositories.dto.ProductDto
import ru.skriplenok.shoppinglist.repositories.dto.ShoppingDto

@Database(entities = [ShoppingDto::class, ProductDto::class], version = 1)
abstract class RoomAppDatabase: RoomDatabase() {

    abstract fun shoppingDao(): ShoppingDao
    abstract fun productDao(): ProductDao

    companion object {
        fun getDatabase(context: Context): RoomAppDatabase =
            Room.databaseBuilder(context, RoomAppDatabase::class.java, RoomContract.DATABASE_NAME).build()
    }
}