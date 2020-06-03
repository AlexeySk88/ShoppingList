package ru.skriplenok.shoppinglist.repositories.dto

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import ru.skriplenok.shoppinglist.repositories.contracts.RoomContract
import java.util.*

@Entity(
    tableName = RoomContract.TABLE_PRODUCTS,
    foreignKeys = [
        ForeignKey(entity = ShoppingDto::class, parentColumns = ["id"], childColumns = ["shopping_id"], onDelete = CASCADE),
        ForeignKey(entity = ProductTypeDto::class, parentColumns = ["id"], childColumns = ["type_id"])
    ],
    indices = [Index("shopping_id"), Index("type_id")]
)
data class ProductDto(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "shopping_id")
    var shoppingId: Int,

    @ColumnInfo(name = "type_id")
    val typeId: Int,

    val name: String,

    val quantity: String,

    @ColumnInfo(name = "created_date")
    val createdDate: Calendar = Calendar.getInstance(),

    @ColumnInfo(name = "selected_date")
    var selectedDate: Calendar? = null
){
}