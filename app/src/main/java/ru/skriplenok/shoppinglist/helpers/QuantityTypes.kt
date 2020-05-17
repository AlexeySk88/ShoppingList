package ru.skriplenok.shoppinglist.helpers

import ru.skriplenok.shoppinglist.repositories.dto.ProductTypeDto

class QuantityTypes private constructor(list: List<ProductTypeDto>){

    var list: List<ProductTypeDto> = list
        private set

    var map: Map<Int, ProductTypeDto> = listToMap(list)
        private set

    private fun listToMap(list: List<ProductTypeDto>): Map<Int, ProductTypeDto> {
        val map = mutableMapOf<Int, ProductTypeDto>()
        for (item in list) {
            map[item.id] = item
        }
        return map
    }

    companion object {

        private var instance: QuantityTypes? = null

        fun setInstance(list: List<ProductTypeDto>) {
            instance = QuantityTypes(list)
        }

        fun getInstance() = instance!!
    }
}