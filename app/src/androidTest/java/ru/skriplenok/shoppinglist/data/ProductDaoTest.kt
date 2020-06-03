package ru.skriplenok.shoppinglist.data

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.skriplenok.shoppinglist.repositories.RoomAppDatabase
import ru.skriplenok.shoppinglist.repositories.dao.ProductDao
import ru.skriplenok.shoppinglist.repositories.dao.ProductTypeDao
import ru.skriplenok.shoppinglist.repositories.dao.ShoppingDao
import ru.skriplenok.shoppinglist.repositories.dto.ProductDto
import ru.skriplenok.shoppinglist.repositories.dto.ProductTypeDto
import ru.skriplenok.shoppinglist.repositories.dto.ShoppingDto

@RunWith(AndroidJUnit4::class)
class ProductDaoTest {

    private lateinit var database: RoomAppDatabase

    private lateinit var shoppingDao: ShoppingDao
    private val shopping1 = ShoppingDto(1, "A")

    private lateinit var type: ProductTypeDao
    private val type1 = ProductTypeDto(1, "", "")

    private lateinit var productDao: ProductDao
    private val product1 = ProductDto(1, 1, 1, "a", "1")
    private val product2 = ProductDto(2, 1, 1, "b", "2")
    private val product3 = ProductDto(3, 1, 1, "c", "3")

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, RoomAppDatabase::class.java).build()
        shoppingDao = database.shoppingDao()
        productDao = database.productDao()
        type = database.productTypeDao()
        type.insert(type1)
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testOnDeleteCascade() = runBlocking {
        val shoppingList = listOf(shopping1)
        shoppingDao.insertAll(shoppingList)
        productDao.insertAll(listOf(product1, product2, product3))
        assertEquals(3, productDao.getAll().size)

        shoppingDao.deleteAll(shoppingList)
        assertEquals(0, productDao.getAll().size)
    }
}