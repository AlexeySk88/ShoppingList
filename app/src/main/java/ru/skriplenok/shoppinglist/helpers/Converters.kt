package ru.skriplenok.shoppinglist.helpers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.skriplenok.shoppinglist.models.ProductModel
import ru.skriplenok.shoppinglist.models.ShoppingModel
import ru.skriplenok.shoppinglist.repositories.dto.ProductDto
import ru.skriplenok.shoppinglist.repositories.dto.ShoppingDto
import ru.skriplenok.shoppinglist.repositories.dto.ShoppingWithCount

object Converters {

    private val quantityTypes = QuantityTypes.getInstance()

    //TODO добавить обработчик ошибок
    fun productDtoToProductModel(dto: ProductDto): ProductModel {
        val quantityType = quantityTypes.map[dto.typeId] ?: error("")
        return ProductModel(dto, quantityType.shortName)
    }

    fun productDtoToProductModel(dtoList: List<ProductDto>): LiveData<MutableList<ProductModel>> {
        val models = MutableLiveData<MutableList<ProductModel>>(mutableListOf())
        for (dto in dtoList) {
            models.value?.add(productDtoToProductModel(dto))
        }
        return models
    }

    private fun productModelToProductDto(model: ProductModel) = model.product

    fun productModelToProductDto(modelList: List<ProductModel>): List<ProductDto> {
        val dtoList = mutableListOf<ProductDto>()
        for (model in modelList) {
            dtoList.add(productModelToProductDto(model))
        }
        return dtoList
    }

    private fun shoppingDtoToShoppingModel(dto: ShoppingWithCount): ShoppingModel {
        return ShoppingModel(dto.shopping, dto.productsAll,
                             dto.productsActive)
    }

    fun shoppingDtoToShoppingModel(dtoList: List<ShoppingWithCount>): LiveData<MutableList<ShoppingModel>> {
        val modelList = MutableLiveData<MutableList<ShoppingModel>>(mutableListOf())
        for (dto in dtoList) {
            modelList.value?.add(shoppingDtoToShoppingModel(dto))
        }
        return modelList
    }
}