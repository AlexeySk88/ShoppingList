package ru.skriplenok.shoppinglist.repositories.dao

import androidx.room.*
import ru.skriplenok.shoppinglist.repositories.contracts.RoomContract
import ru.skriplenok.shoppinglist.repositories.dto.ShoppingDto
import ru.skriplenok.shoppinglist.repositories.dto.ShoppingProducts
import ru.skriplenok.shoppinglist.repositories.dto.ShoppingWithCount

@Dao
interface ShoppingDao {

    @Transaction
    @Query("SELECT ${ RoomContract.TABLE_SHOPPING}.id, " +
            "${ RoomContract.TABLE_SHOPPING}.name, " +
            "${ RoomContract.TABLE_SHOPPING}.created_date, closed_date, " +
            "COUNT(${RoomContract.TABLE_PRODUCTS}.id) AS products_all, " +
            "COUNT(${RoomContract.TABLE_PRODUCTS}.selected_date) AS products_active " +
            "FROM ${ RoomContract.TABLE_SHOPPING}, ${RoomContract.TABLE_PRODUCTS} " +
            "WHERE ${ RoomContract.TABLE_SHOPPING}.id = ${RoomContract.TABLE_PRODUCTS}.shopping_id " +
            "AND closed_date IS NULL " +
            "GROUP BY ${ RoomContract.TABLE_SHOPPING}.id")
    suspend fun getAllActive(): List<ShoppingWithCount>

    @Transaction
    @Query("SELECT ${ RoomContract.TABLE_SHOPPING}.id, " +
            "${ RoomContract.TABLE_SHOPPING}.name, " +
            "${ RoomContract.TABLE_SHOPPING}.created_date, closed_date, " +
            "COUNT(${RoomContract.TABLE_PRODUCTS}.id) AS products_all, " +
            "COUNT(${RoomContract.TABLE_PRODUCTS}.selected_date) AS products_active " +
            "FROM ${ RoomContract.TABLE_SHOPPING}, ${RoomContract.TABLE_PRODUCTS} " +
            "WHERE ${ RoomContract.TABLE_SHOPPING}.id = ${RoomContract.TABLE_PRODUCTS}.shopping_id " +
            "AND closed_date IS NOT NULL " +
            "GROUP BY ${ RoomContract.TABLE_SHOPPING}.id")
    suspend fun getAllArchive(): List<ShoppingWithCount>

    @Transaction
    @Query( "SELECT * FROM ${RoomContract.TABLE_SHOPPING} WHERE id = :shoppingId")
    fun getWithProducts(shoppingId: Int): ShoppingProducts

    @Insert
    fun insert(dto: ShoppingDto)

    @Insert
    fun insertAll(dtoList: List<ShoppingDto>)
}