package ru.skriplenok.shoppinglist.viewmodel.creator

import androidx.databinding.ObservableField
import kotlinx.coroutines.runBlocking
import ru.skriplenok.shoppinglist.models.ProductModel
import ru.skriplenok.shoppinglist.models.ShoppingIdWithTitle
import ru.skriplenok.shoppinglist.repositories.ProductRepository
import ru.skriplenok.shoppinglist.repositories.ShoppingRepository
import ru.skriplenok.shoppinglist.repositories.dto.ShoppingDto

class NewCreatorState(
    shoppingRepository: ShoppingRepository,
    productRepository: ProductRepository
): CreatorState(shoppingRepository, productRepository) {

    override fun shoppingSave(shoppingTitle: String, productList: List<ProductModel>) {
        runBlocking {
            val shoppingDto = ShoppingDto(name = shoppingTitle)
            val shoppingId = shoppingRepository.insert(shoppingDto)
            setShoppingId(shoppingId, productList)
            productRepository.insertAll(productList)
        }
    }

    override fun setTitleAndProductList(
        shoppingIdWithTitle: ShoppingIdWithTitle?,
        title: ObservableField<String>,
        productList: MutableList<ProductModel>
    ) {
        //Ничего не делаем, т.к. новый список
    }
}