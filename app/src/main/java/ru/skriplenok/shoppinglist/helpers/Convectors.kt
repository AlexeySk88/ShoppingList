package ru.skriplenok.shoppinglist.helpers

import ru.skriplenok.shoppinglist.models.ProductsModel
import ru.skriplenok.shoppinglist.models.QuantityTypeModel
import ru.skriplenok.shoppinglist.repositories.dto.ProductDto
import ru.skriplenok.shoppinglist.repositories.dto.ProductTypeDto

object Convectors {

    fun productTypeDtoToQuantityTypeModel(dto: ProductTypeDto): QuantityTypeModel {
        return QuantityTypeModel(dto.id, dto.shortName, dto.fullName)
    }

    fun productTypeDtoToQuantityTypeModel(dtoList: List<ProductTypeDto>): List<QuantityTypeModel> {
        val models = mutableListOf<QuantityTypeModel>()
        for (dto in dtoList) {
            models.add(productTypeDtoToQuantityTypeModel(dto))
        }

        return models
    }

    fun productDtoToProductModel(dto: ProductDto): ProductsModel {
        return ProductsModel(dto.id, dto.name, "1 шт.", dto.selectedDate == null)
    }

    fun productDtoToProductModel(dtoList: List<ProductDto>): List<ProductsModel> {
        val models = mutableListOf<ProductsModel>()
        for (dto in dtoList) {
            models.add(productDtoToProductModel(dto))
        }

        return models
    }
}