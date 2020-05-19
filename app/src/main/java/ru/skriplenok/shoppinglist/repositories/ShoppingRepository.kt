package ru.skriplenok.shoppinglist.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.skriplenok.shoppinglist.helpers.Converters
import ru.skriplenok.shoppinglist.models.ShoppingModel
import ru.skriplenok.shoppinglist.repositories.dao.ShoppingDao
import ru.skriplenok.shoppinglist.repositories.dto.ShoppingDto
import ru.skriplenok.shoppinglist.repositories.dto.ShoppingWithCount

class ShoppingRepository private constructor(private val dao: ShoppingDao){

    //TODO добавить сохранение резульата выборки и его инвалидации при UID
    suspend fun getAllActive(): MutableList<ShoppingModel> {
        return Converters.shoppingDtoToShoppingModel(dao.getAllActive())
    }

    suspend fun updateAll(modelList: List<ShoppingModel>) {
        dao.updateAll(Converters.shoppingModelToShoppingDto(modelList))
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