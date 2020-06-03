package ru.skriplenok.shoppinglist.helpers

import ru.skriplenok.shoppinglist.models.ProductModel
import ru.skriplenok.shoppinglist.models.ShoppingModel
import ru.skriplenok.shoppinglist.repositories.dto.ProductDto
import ru.skriplenok.shoppinglist.repositories.dto.ShoppingDto
import ru.skriplenok.shoppinglist.repositories.dto.ShoppingWithCount

object Converters {

    private val quantityTypes = QuantityTypes.getInstance()

    // PRODUCT

    //TODO добавить обработчик ошибок
    fun productDtoToProductModel(dto: ProductDto): ProductModel {
        val quantityType = quantityTypes.map[dto.typeId] ?: error("")
        return ProductModel(dto, quantityType.shortName)
    }

    fun productDtoToProductModel(dtoList: List<ProductDto>): MutableList<ProductModel> {
        val models = mutableListOf<ProductModel>()
        for (dto in dtoList) {
            models.add(productDtoToProductModel(dto))
        }
        return models
    }

    fun productModelToProductDto(model: ProductModel) = model.product

    fun productModelToProductDto(modelList: List<ProductModel>): List<ProductDto> {
        val dtoList = mutableListOf<ProductDto>()
        for (model in modelList) {
            dtoList.add(productModelToProductDto(model))
        }
        return dtoList
    }

    fun productModelListToMap(list: List<ProductModel>): Map<Int, ProductModel> {
        val result: MutableMap<Int, ProductModel> = mutableMapOf()
        for (productModel in list) {
            result[productModel.product.id] = productModel
        }
        return result
    }

    fun productModelMapToList(map: Map<Int, ProductModel>): List<ProductModel> {
        val result: MutableList<ProductModel> = mutableListOf()
        for (entry in map) {
            result.add(entry.value)
        }
        return result
    }

    // SHOPPING

    private fun shoppingDtoToShoppingModel(dto: ShoppingWithCount): ShoppingModel {
        return ShoppingModel(dto.shopping, dto.productsAll, dto.productsActive)
    }

    fun shoppingDtoToShoppingModel(dtoList: List<ShoppingWithCount>): MutableList<ShoppingModel> {
        val modelList = mutableListOf<ShoppingModel>()
        for (dto in dtoList) {
            modelList.add(shoppingDtoToShoppingModel(dto))
        }
        return modelList
    }

    private fun shoppingModelToShoppingDto(model: ShoppingModel) = model.shopping

    fun shoppingModelToShoppingDto(modelList: List<ShoppingModel>): List<ShoppingDto> {
        val dtoList = mutableListOf<ShoppingDto>()
        for (model in modelList) {
            dtoList.add(shoppingModelToShoppingDto(model))
        }
        return dtoList
    }
}