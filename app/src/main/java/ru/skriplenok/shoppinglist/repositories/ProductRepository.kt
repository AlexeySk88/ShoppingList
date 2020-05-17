package ru.skriplenok.shoppinglist.repositories

import ru.skriplenok.shoppinglist.helpers.Converters
import ru.skriplenok.shoppinglist.models.ProductModel
import ru.skriplenok.shoppinglist.repositories.dao.ProductDao
import ru.skriplenok.shoppinglist.repositories.dto.ProductDto

class ProductRepository private constructor(private val dao: ProductDao){

    suspend fun getByShoppingId(id: Int): List<ProductDto> {
        return dao.getByShoppingId(id)
    }

    suspend fun insertAll(list: List<ProductDto>) {
        dao.insertAll(list)
    }

    suspend fun insertAllModel(list: List<ProductModel>, shoppingId: Int) {
        val dtoList = Converters.productModelToProductDto(list, shoppingId)
        dao.insertAll(dtoList)
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