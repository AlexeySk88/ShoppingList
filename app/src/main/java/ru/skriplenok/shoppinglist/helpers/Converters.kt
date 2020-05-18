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
    private fun productDtoToProductModel(dto: ProductDto): ProductModel {
        val quantityType = quantityTypes.map[dto.typeId] ?: error("")
        return ProductModel(dto.id, dto.name, dto.quantity, quantityType.shortName,
                            quantityType.id, dto.selectedDate == null)
    }

    fun productDtoToProductModel(dtoList: List<ProductDto>): List<ProductModel> {
        val models = mutableListOf<ProductModel>()
        for (dto in dtoList) {
            models.add(productDtoToProductModel(dto))
        }
        return models
    }

    private fun productModelToProductDto(model: ProductModel, shoppingId: Int): ProductDto {
        return ProductDto(shoppingId = shoppingId,typeId = model.typeId,name = model.name,
                          quantity = model.quantity)
    }

    fun productModelToProductDto(modelList: List<ProductModel>, shoppingId: Int): List<ProductDto> {
        val dtoList = mutableListOf<ProductDto>()
        for (model in modelList) {
            dtoList.add(productModelToProductDto(model, shoppingId))
        }
        return dtoList
    }

    private fun shoppingDtoToShoppingModel(dto: ShoppingWithCount): ShoppingModel {
        return ShoppingModel(dto.shopping, dto.productsAll,
                             dto.productsActive, false)
    }

    fun shoppingDtoToShoppingModel(dtoList: List<ShoppingWithCount>): LiveData<MutableList<ShoppingModel>> {
        val modelList = MutableLiveData<MutableList<ShoppingModel>>(mutableListOf())
        for (dto in dtoList) {
            modelList.value?.add(shoppingDtoToShoppingModel(dto))
        }
        return modelList
    }
}