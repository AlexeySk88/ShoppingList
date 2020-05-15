package ru.skriplenok.shoppinglist.repositories.dao

import androidx.room.*
import ru.skriplenok.shoppinglist.repositories.contracts.RoomContract
import ru.skriplenok.shoppinglist.repositories.dto.ShoppingDto

@Dao
interface ShoppingDao {

    @Query("SELECT * FROM ${ RoomContract.TABLE_SHOPPING}")
    fun getAll(): List<ShoppingDto>

    @Insert
    fun insert(dto: ShoppingDto)

    @Update
    fun update(dto: ShoppingDto)

    @Delete
    fun delete(dto: ShoppingDto)
}