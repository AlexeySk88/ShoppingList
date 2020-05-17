package ru.skriplenok.shoppinglist.helpers

import ru.skriplenok.shoppinglist.models.ProductModel
import ru.skriplenok.shoppinglist.repositories.dto.ProductDto

object Convectors {

    private val quantityTypes = QuantityTypes.getInstance()

    //TODO добавить обработчик ошибок
    fun productDtoToProductModel(dto: ProductDto): ProductModel {
        return ProductModel(dto.id, dto.name, dto.quantity.toString(),
            (quantityTypes.map[dto.id] ?: error("")).shortName, dto.selectedDate == null)
    }

    fun productDtoToProductModel(dtoList: List<ProductDto>): List<ProductModel> {
        val models = mutableListOf<ProductModel>()
        for (dto in dtoList) {
            models.add(productDtoToProductModel(dto))
        }

        return models
    }
}