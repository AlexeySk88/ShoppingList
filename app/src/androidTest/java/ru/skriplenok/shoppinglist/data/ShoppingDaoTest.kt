package ru.skriplenok.shoppinglist.data

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Assert.*
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
import java.util.*

@RunWith(AndroidJUnit4::class)
class ShoppingDaoTest {

    private lateinit var database: RoomAppDatabase
    private val datetime = Calendar.getInstance()

    private lateinit var shoppingDao: ShoppingDao
    private val shopping1 = ShoppingDto(1, "A")
    private val shopping2 = ShoppingDto(2, "B", datetime, null)
    private val shopping3 = ShoppingDto(3, "C", datetime, datetime)

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
        shoppingDao.insertAll(listOf(shopping1, shopping2, shopping3))

        type = database.productTypeDao()
        type.insert(type1)

        productDao = database.productDao()
        productDao.insertAll(listOf(product1, product2, product3))
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGetAllShopping() = runBlocking{
        val activeList = shoppingDao.getAllActive()
        val archiveList = shoppingDao.getAllArchive()
        val size = activeList.size + archiveList.size
        assertEquals(3, size)
    }

    @Test
    fun testGetAllActive() = runBlocking{
        val activeList = shoppingDao.getAllActive()

        assertEquals(2, activeList.size)
        assertThat(activeList[0].shopping, equalTo(shopping1))
        assertThat(activeList[1].shopping, equalTo(shopping2))
    }

    @Test
    fun testGetAllArchive() = runBlocking{
        val archiveList = shoppingDao.getAllArchive()

        assertEquals(1, archiveList.size)
        assertThat(archiveList[0].shopping, equalTo(shopping3))
    }

    @Test
    fun testAutoIncrement() = runBlocking {
        val shopping4 = ShoppingDto(name = "D")
        val id = shoppingDao.insert(shopping4)

        assertNotNull(id)
    }
}