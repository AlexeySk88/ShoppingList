package ru.skriplenok.shoppinglist.repositories.dao

import androidx.room.*
import ru.skriplenok.shoppinglist.repositories.contracts.RoomContract
import ru.skriplenok.shoppinglist.repositories.dto.ProductDto

@Dao
interface ProductDao {

    @Query("SELECT * FROM ${RoomContract.TABLE_PRODUCTS} WHERE shopping_id = :id")
    suspend fun getByShoppingId(id: Int): List<ProductDto>

    @Insert
    fun insert(dto: ProductDto)

    @Insert
    fun insertAll(dtoList: List<ProductDto>)
}