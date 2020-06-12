package ru.skriplenok.shoppinglist.repositories

import ru.skriplenok.shoppinglist.helpers.Converters
import ru.skriplenok.shoppinglist.models.ShoppingModel
import ru.skriplenok.shoppinglist.repositories.dao.ShoppingDao
import ru.skriplenok.shoppinglist.repositories.dto.ShoppingDto
import javax.inject.Inject

class ShoppingRepository @Inject constructor(private val dao: ShoppingDao){

    //TODO добавить сохранение резульата выборки и его инвалидации при UID
    suspend fun getAllActive(): List<ShoppingModel> {
        return Converters.shoppingDtoToShoppingModel(dao.getAllActive())
    }

    suspend fun updateTitleById(shoppingId: Int, newName: String) {
        dao.updateTitleById(shoppingId, newName)
    }

    suspend fun updateAll(modelList: List<ShoppingModel>) {
        dao.updateAll(Converters.shoppingModelToShoppingDto(modelList))
    }

    suspend fun insert(dto: ShoppingDto) = dao.insert(dto).toInt()

    suspend fun deleteAll(modelList: List<ShoppingModel>) {
        dao.deleteAll(Converters.shoppingModelToShoppingDto(modelList))
    }
}