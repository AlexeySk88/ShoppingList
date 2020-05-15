package ru.skriplenok.shoppinglist.repositories.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.skriplenok.shoppinglist.repositories.contracts.RoomContract

// TODO продумать хранение QuantityType
@Entity(tableName = RoomContract.TABLE_PRODUCTS)
data class ProductDto(@PrimaryKey val id: Int, val shoppingId: Int, val name: String,
                      val quantity: Float){
}