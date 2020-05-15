package ru.skriplenok.shoppinglist.repositories.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.skriplenok.shoppinglist.repositories.contracts.RoomContract

// TODO продумать: кто создал список, кому расшарен список
@Entity(tableName = RoomContract.TABLE_SHOPPING)
data class ShoppingDto(@PrimaryKey val id: Int, val name: String, val isActive: Boolean) {
}