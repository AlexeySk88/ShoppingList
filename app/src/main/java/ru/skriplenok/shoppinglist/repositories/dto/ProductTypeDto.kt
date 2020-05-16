package ru.skriplenok.shoppinglist.repositories.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.skriplenok.shoppinglist.repositories.contracts.RoomContract

@Entity(tableName = RoomContract.TABLE_PRODUCT_TYPE)
data class ProductTypeDto(

    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "full_name")
    val fullName:String,

    @ColumnInfo(name = "short_name")
    val shortName: String
) {
}