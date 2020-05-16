package ru.skriplenok.shoppinglist.repositories.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ru.skriplenok.shoppinglist.repositories.contracts.RoomContract
import ru.skriplenok.shoppinglist.repositories.dto.ProductTypeDto

@Dao
interface ProductTypeDao {

    @Query("SELECT * FROM ${RoomContract.TABLE_PRODUCT_TYPE}")
    fun getAll(): List<ProductTypeDto>

    @Insert
    fun insert(dto: ProductTypeDto)

    @Insert
    fun insertAll(dtoList: List<ProductTypeDto>)
}