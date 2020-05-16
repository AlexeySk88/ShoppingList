package ru.skriplenok.shoppinglist.helpers

import ru.skriplenok.shoppinglist.models.QuantityTypeModel
import ru.skriplenok.shoppinglist.repositories.dto.ProductTypeDto

object Convectors {

    fun productTypeToQuantityType(dto: ProductTypeDto): QuantityTypeModel {
        return QuantityTypeModel(dto.id, dto.shortName, dto.fullName)
    }

    fun productTypeListToQuantityTypeList(dtoList: List<ProductTypeDto>): List<QuantityTypeModel> {
        val models = mutableListOf<QuantityTypeModel>()
        for(dto in dtoList) {
            models.add(productTypeToQuantityType(dto))
        }

        return models
    }
}