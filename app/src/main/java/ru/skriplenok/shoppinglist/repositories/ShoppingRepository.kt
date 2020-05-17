package ru.skriplenok.shoppinglist.repositories

import ru.skriplenok.shoppinglist.repositories.dao.ShoppingDao
import ru.skriplenok.shoppinglist.repositories.dto.ShoppingDto
import ru.skriplenok.shoppinglist.repositories.dto.ShoppingWithCount

class ShoppingRepository private constructor(private val dao: ShoppingDao){

    //TODO добавить сохранение резульата выборки и его инвалидации при UID
    suspend fun getAllActive(): List<ShoppingWithCount> {
        return dao.getAllActive()
    }

    suspend fun insert(dto: ShoppingDto) = dao.insert(dto).toInt()

    companion object {

        @Volatile
        private var instance: ShoppingRepository? = null

        fun getInstance(dao: ShoppingDao): ShoppingRepository {
            return instance
                ?: synchronized(this) {
                    instance ?: ShoppingRepository(dao).also { instance = it }
                }
        }
    }
}