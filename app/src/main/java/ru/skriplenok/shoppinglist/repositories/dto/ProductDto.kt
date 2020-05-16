package ru.skriplenok.shoppinglist.repositories.dto

import androidx.room.*
import ru.skriplenok.shoppinglist.repositories.contracts.RoomContract
import java.util.*

@Entity(
    tableName = RoomContract.TABLE_PRODUCTS,
    foreignKeys = [
        ForeignKey(entity = ShoppingDto::class, parentColumns = ["id"], childColumns = ["shopping_id"]),
        ForeignKey(entity = ProductTypeDto::class, parentColumns = ["id"], childColumns = ["type_id"])
    ],
    indices = [Index("shopping_id"), Index("type_id")]
)
data class ProductDto(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "shopping_id")
    val shoppingId: Int,

    @ColumnInfo(name = "type_id")
    val typeId: Int,

    val name: String,

    val quantity: Float,

    @ColumnInfo(name = "created_date")
    val createdDate: Calendar = Calendar.getInstance(),

    @ColumnInfo(name = "selected_date")
    val selectedDate: Calendar? = null
){
}