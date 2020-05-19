package ru.skriplenok.shoppinglist.repositories.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.skriplenok.shoppinglist.repositories.contracts.RoomContract
import java.util.*

// TODO продумать: кто создал список, кому расшарен список
@Entity(tableName = RoomContract.TABLE_SHOPPING)
data class ShoppingDto(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    var name: String,

    @ColumnInfo(name = "created_date")
    val createdDate: Calendar = Calendar.getInstance(),

    @ColumnInfo(name = "closed_date")
    var closedDate: Calendar? = null
) {
}