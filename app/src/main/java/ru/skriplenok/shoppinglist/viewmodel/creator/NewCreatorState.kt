package ru.skriplenok.shoppinglist.viewmodel.creator

import kotlinx.coroutines.runBlocking
import ru.skriplenok.shoppinglist.models.CreatorModel
import ru.skriplenok.shoppinglist.models.ProductModel
import ru.skriplenok.shoppinglist.models.ShoppingIdWithTitle
import ru.skriplenok.shoppinglist.repositories.ProductRepository
import ru.skriplenok.shoppinglist.repositories.ShoppingRepository
import ru.skriplenok.shoppinglist.repositories.dto.ShoppingDto

class NewCreatorState(
    shoppingRepository: ShoppingRepository,
    productRepository: ProductRepository
): CreatorState(shoppingRepository, productRepository) {

    override fun shoppingSave(creatorModel: CreatorModel) {
        runBlocking {
            val shoppingDto = ShoppingDto(name = creatorModel.title)
            val shoppingId = shoppingRepository.insert(shoppingDto)
            setShoppingId(shoppingId, creatorModel.productList)
            productRepository.insertAll(creatorModel.productList)
        }
    }

    override fun setTitleAndProductList(
        shoppingIdWithTitle: ShoppingIdWithTitle?,
        creatorModel: CreatorModel
    ) {
        //Ничего не делаем, т.к. новый список
    }
}