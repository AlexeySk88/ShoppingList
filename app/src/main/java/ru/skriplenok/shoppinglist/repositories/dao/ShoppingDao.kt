package ru.skriplenok.shoppinglist.repositories.dao

import androidx.room.*
import ru.skriplenok.shoppinglist.repositories.contracts.RoomContract
import ru.skriplenok.shoppinglist.repositories.dto.ShoppingDto
import ru.skriplenok.shoppinglist.repositories.dto.ShoppingWithCount

@Dao
interface ShoppingDao {

    @Transaction
    @Query("""
            SELECT ${ RoomContract.TABLE_SHOPPING}.id,
            ${RoomContract.TABLE_SHOPPING}.name,
            ${RoomContract.TABLE_SHOPPING}.created_date, closed_date,
            COUNT(${RoomContract.TABLE_PRODUCTS}.id) AS products_all,
            COUNT(${RoomContract.TABLE_PRODUCTS}.selected_date) AS products_active
            FROM ${RoomContract.TABLE_SHOPPING}, ${RoomContract.TABLE_PRODUCTS}
            WHERE ${RoomContract.TABLE_SHOPPING}.id = ${RoomContract.TABLE_PRODUCTS}.shopping_id
            AND closed_date IS NULL
            GROUP BY ${RoomContract.TABLE_SHOPPING}.id
            """)
    suspend fun getAllActive(): List<ShoppingWithCount>

    @Transaction
    @Query("""
            SELECT ${ RoomContract.TABLE_SHOPPING}.id,
            ${RoomContract.TABLE_SHOPPING}.name,
            ${RoomContract.TABLE_SHOPPING}.created_date, closed_date,
            COUNT(${RoomContract.TABLE_PRODUCTS}.id) AS products_all,
            COUNT(${RoomContract.TABLE_PRODUCTS}.selected_date) AS products_active
            FROM ${RoomContract.TABLE_SHOPPING}, ${RoomContract.TABLE_PRODUCTS}
            WHERE ${RoomContract.TABLE_SHOPPING}.id = ${RoomContract.TABLE_PRODUCTS}.shopping_id
            AND closed_date IS NOT NULL 
            GROUP BY ${RoomContract.TABLE_SHOPPING}.id
            """)
    suspend fun getAllArchive(): List<ShoppingWithCount>

    @Insert
    suspend fun insert(dto: ShoppingDto): Long

    @Insert
    fun insertAll(dtoList: List<ShoppingDto>)

    @Update
    suspend fun updateAll(dtoList: List<ShoppingDto>)

    @Query("UPDATE ${RoomContract.TABLE_SHOPPING} SET name = :newName WHERE id = :shoppingId")
    suspend fun updateTitleById(shoppingId: Int, newName: String)

    @Delete
    suspend fun deleteAll(dtoList: List<ShoppingDto>)
}