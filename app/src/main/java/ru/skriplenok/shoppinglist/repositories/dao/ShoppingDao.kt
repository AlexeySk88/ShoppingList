package ru.skriplenok.shoppinglist.repositories.dao

import androidx.room.*
import ru.skriplenok.shoppinglist.repositories.contracts.RoomContract
import ru.skriplenok.shoppinglist.repositories.dto.ShoppingDto
import ru.skriplenok.shoppinglist.repositories.dto.ShoppingProducts

@Dao
interface ShoppingDao {

    @Query("SELECT * FROM ${ RoomContract.TABLE_SHOPPING} WHERE closed_date IS NULL")
    fun getAllActive(): List<ShoppingDto>

    @Query("SELECT * FROM ${ RoomContract.TABLE_SHOPPING} WHERE closed_date IS NOT NULL")
    fun getAllArchive(): List<ShoppingDto>

    @Transaction
    @Query( "SELECT * FROM ${RoomContract.TABLE_SHOPPING} WHERE id = :shoppingId")
    fun getWithProducts(shoppingId: Int): ShoppingProducts

    @Insert
    fun insert(dto: ShoppingDto)

    @Insert
    fun insertAll(dtoList: List<ShoppingDto>)
}