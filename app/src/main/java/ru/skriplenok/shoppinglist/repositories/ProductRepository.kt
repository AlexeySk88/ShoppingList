package ru.skriplenok.shoppinglist.repositories

import ru.skriplenok.shoppinglist.helpers.Converters
import ru.skriplenok.shoppinglist.models.ProductModel
import ru.skriplenok.shoppinglist.repositories.dao.ProductDao
import javax.inject.Inject

class ProductRepository @Inject constructor(private val dao: ProductDao){

    suspend fun getByShoppingId(id: Int): List<ProductModel> {
        val dto = dao.getByShoppingId(id)
        return Converters.productDtoToProductModel(dto)
    }

    suspend fun insert(model: ProductModel) {
        dao.insert(Converters.productModelToProductDto(model))
    }

    suspend fun insertAll(list: List<ProductModel>) {
        dao.insertAll(Converters.productModelToProductDto(list))
    }

    suspend fun update(model: ProductModel) {
        dao.update(Converters.productModelToProductDto(model))
    }

    suspend fun updateAll(list: List<ProductModel>) {
        dao.updateAll(Converters.productModelToProductDto(list))
    }

    suspend fun deleteAll(list: List<ProductModel>) {
        dao.deleteAll(Converters.productModelToProductDto(list))
    }
}