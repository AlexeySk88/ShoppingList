package ru.skriplenok.shoppinglist.repositories

import ru.skriplenok.shoppinglist.helpers.Convectors
import ru.skriplenok.shoppinglist.models.QuantityTypeModel
import ru.skriplenok.shoppinglist.repositories.dao.ProductTypeDao

class ProductTypeRepository private constructor(private val dao: ProductTypeDao){

    private var model: List<QuantityTypeModel>? = null

    suspend fun getAll(): List<QuantityTypeModel> {
        return model ?: Convectors.productTypeDtoToQuantityTypeModel(dao.getAll()).also { model = it }
    }

    companion object {

        @Volatile
        private var instance: ProductTypeRepository? = null

        fun getInstance(dao: ProductTypeDao): ProductTypeRepository {
            return instance
                ?: synchronized(this) {
                instance ?: ProductTypeRepository(dao).also { instance = it }
            }
        }
    }
}