package ru.skriplenok.shoppinglist.repositories

import androidx.lifecycle.LiveData
import ru.skriplenok.shoppinglist.helpers.Converters
import ru.skriplenok.shoppinglist.models.ProductModel
import ru.skriplenok.shoppinglist.repositories.dao.ProductDao

class ProductRepository private constructor(private val dao: ProductDao){

    suspend fun getByShoppingId(id: Int): LiveData<MutableList<ProductModel>> {
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

    companion object {

        @Volatile
        private var instance: ProductRepository? = null

        fun getInstance(dao: ProductDao): ProductRepository {
            return instance
                ?: synchronized(this) {
                    instance ?: ProductRepository(dao).also { instance = it }
                }
        }
    }
}