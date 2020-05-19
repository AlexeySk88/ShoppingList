package ru.skriplenok.shoppinglist.repositories

import androidx.lifecycle.LiveData
import ru.skriplenok.shoppinglist.helpers.Converters
import ru.skriplenok.shoppinglist.models.ProductModel
import ru.skriplenok.shoppinglist.repositories.dao.ProductDao
import ru.skriplenok.shoppinglist.repositories.dto.ProductDto

class ProductRepository private constructor(private val dao: ProductDao){

    suspend fun getByShoppingId(id: Int): LiveData<MutableList<ProductModel>> {
        val dto = dao.getByShoppingId(id)
        return Converters.productDtoToProductModel(dto)
    }

    suspend fun insertAll(list: List<ProductDto>) {
        dao.insertAll(list)
    }

    suspend fun insertAllModel(list: List<ProductModel>) {
        val dtoList = Converters.productModelToProductDto(list)
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