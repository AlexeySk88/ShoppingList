package ru.skriplenok.shoppinglist.data

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.skriplenok.shoppinglist.repositories.RoomAppDatabase
import ru.skriplenok.shoppinglist.repositories.dao.ProductTypeDao
import ru.skriplenok.shoppinglist.repositories.dto.ProductTypeDto

@RunWith(AndroidJUnit4::class)
class ProductTypeDaoTest {

    private lateinit var database: RoomAppDatabase
    private lateinit var dao: ProductTypeDao
    private val type1 = ProductTypeDto(1, "грамм", "гр.")
    private val type2 = ProductTypeDto(2, "килограм", "кг.")
    private val type3 = ProductTypeDto(3, "миллилитр", "мл.")

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, RoomAppDatabase::class.java).build()
        dao = database.productTypeDao()

        dao.insertAll(listOf(type1, type2, type3))
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGetAllProductType() = runBlocking {
        val productTypeList = dao.getAll()
        assertEquals(3, productTypeList.size)

        assertThat(productTypeList[0], equalTo(type1))
        assertThat(productTypeList[1], equalTo(type2))
        assertThat(productTypeList[2], equalTo(type3))
    }
}