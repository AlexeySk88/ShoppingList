package ru.skriplenok.shoppinglist

import androidx.databinding.ObservableField
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
import ru.skriplenok.shoppinglist.helpers.Converters
import ru.skriplenok.shoppinglist.models.ShoppingIdWithTitle
import ru.skriplenok.shoppinglist.repositories.ProductRepository
import ru.skriplenok.shoppinglist.repositories.RoomAppDatabase
import ru.skriplenok.shoppinglist.repositories.ShoppingRepository
import ru.skriplenok.shoppinglist.repositories.dao.ProductDao
import ru.skriplenok.shoppinglist.repositories.dao.ProductTypeDao
import ru.skriplenok.shoppinglist.repositories.dao.ShoppingDao
import ru.skriplenok.shoppinglist.repositories.dto.ProductDto
import ru.skriplenok.shoppinglist.repositories.dto.ProductTypeDto
import ru.skriplenok.shoppinglist.repositories.dto.ShoppingDto
import ru.skriplenok.shoppinglist.viewmodel.creator.ChangeState

@RunWith(AndroidJUnit4::class)
class CreatorChangeStateTest {

    private lateinit var database: RoomAppDatabase

    private lateinit var shoppingDao: ShoppingDao
    private val shopping1 = ShoppingDto(1, "A")

    private lateinit var type: ProductTypeDao
    private val type1 = ProductTypeDto(1, "", "")

    private lateinit var productDao: ProductDao
    private val product1 = ProductDto(1, 1, 1, "a", "1")
    private val product2 = ProductDto(2, 1, 1, "b", "2")
    private val product3 = ProductDto(3, 1, 1, "c", "3")

    private lateinit var changeState: ChangeState

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, RoomAppDatabase::class.java).build()

        shoppingDao = database.shoppingDao()
        shoppingDao.insert(shopping1)

        type = database.productTypeDao()
        type.insert(type1)

        productDao = database.productDao()
        productDao.insertAll(listOf(product1, product2, product3))

        changeState = ChangeState(ShoppingRepository(shoppingDao), ProductRepository(productDao))
        setTitleAndProductList()
    }

    @After
    fun closeDb() {
        database.close()
    }

    private fun setTitleAndProductList() {
        val shoppingIdWithTitle = ShoppingIdWithTitle(shopping1.id, shopping1.name)
        val productList =
            Converters.productDtoToProductModel(listOf(product1, product2, product3))
        changeState.setTitleAndProductList(shoppingIdWithTitle, ObservableField(), productList)
    }

    @Test
    fun testShoppingSave() = runBlocking {
        val mutableProduct1 = ProductDto(1, 1, 1, "a", "1")
        val product4 = ProductDto(4, 1, 1, "d", "4")
        val newProductList =
            Converters.productDtoToProductModel(listOf(mutableProduct1, product3, product4))
        changeState.shoppingSave("B", newProductList)

        val shoppingName =  shoppingDao.getAllActive().first().shopping.name
        assertEquals("B", shoppingName)

        val productsDto = productDao.getByShoppingId(1)
        assertEquals(3, productsDto.size)
        assertThat(mutableProduct1, equalTo(productsDto[0]))
        assertThat(product3, equalTo(productsDto[1]))
        assertThat(product4, equalTo(productsDto[2]))
    }
}