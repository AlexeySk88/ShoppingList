package ru.skriplenok.shoppinglist.repositories

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.runBlocking
import ru.skriplenok.shoppinglist.repositories.contracts.RoomContract
import ru.skriplenok.shoppinglist.repositories.dao.ProductDao
import ru.skriplenok.shoppinglist.repositories.dao.ProductTypeDao
import ru.skriplenok.shoppinglist.repositories.dao.ShoppingDao
import ru.skriplenok.shoppinglist.repositories.dto.ProductDto
import ru.skriplenok.shoppinglist.repositories.dto.ProductTypeDto
import ru.skriplenok.shoppinglist.repositories.dto.ShoppingDto
import java.util.concurrent.Executors

@Database(entities = [
    ShoppingDto::class,
    ProductDto::class,
    ProductTypeDto::class
],
    version = 1,
    exportSchema = true)
@TypeConverters(Converters::class)
abstract class RoomAppDatabase: RoomDatabase() {

    abstract fun shoppingDao(): ShoppingDao
    abstract fun productDao(): ProductDao
    abstract fun productTypeDao(): ProductTypeDao

    companion object {

        @Volatile
        private var instance: RoomAppDatabase? = null

        fun getDatabase(context: Context): RoomAppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): RoomAppDatabase =
            Room.databaseBuilder(context, RoomAppDatabase::class.java, RoomContract.DATABASE_NAME)
                .addCallback(object: Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Executors.newSingleThreadScheduledExecutor()
                            .execute {
                                getDatabase(context).apply {
                                    shoppingDao().insertAll(shoppingList)
                                    productTypeDao().insertAll(typeList)

                                    runBlocking {
                                        productDao().insertAll(productList)
                                    }
                                }
                            }
                    }
                })
                .build()

        private val shoppingList = listOf(
            ShoppingDto(1, "Продукты на неделю"),
            ShoppingDto(2, "Бытовая химия")
        )

        private val productList = listOf(
            ProductDto(1, 1, 5,"Горошек", "1"),
            ProductDto(2, 1, 5,"Докторская колбаса", "1"),
            ProductDto(3, 1, 5,"Огурец конс.", "1"),
            ProductDto(4, 1, 5, "Майонез", "1"),

            ProductDto(5, 2, 5, "Чай", "1"),
            ProductDto(6, 2, 5, "Печенье", "1"),
            ProductDto(7, 2, 5, "Сгущенка", "1")
        )

        private val typeList = listOf(
            ProductTypeDto(1, "грамм", "гр."),
            ProductTypeDto(2, "килограм", "кг."),
            ProductTypeDto(3, "миллилитр", "мл."),
            ProductTypeDto(4, "литр", "л."),
            ProductTypeDto(5, "упаковка", "уп.")
        )
    }
}