package ru.skriplenok.shoppinglist.repositories.dao

import androidx.room.*
import ru.skriplenok.shoppinglist.repositories.contracts.RoomContract
import ru.skriplenok.shoppinglist.repositories.dto.ProductDto

@Dao
interface ProductDao {

    @Query("SELECT * FROM ${RoomContract.TABLE_PRODUCTS} WHERE shoppingId = :id")
    fun getByShoppingId(id: Int): List<ProductDto>

    @Insert
    fun insert(dto: ProductDto)

    @Update
    fun update(dto: ProductDto)

    @Delete
    fun delete(dto: ProductDto)
}