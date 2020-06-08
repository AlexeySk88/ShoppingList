package ru.skriplenok.shoppinglist.viewmodel.creator

import ru.skriplenok.shoppinglist.models.CreatorModel
import ru.skriplenok.shoppinglist.models.ProductModel
import ru.skriplenok.shoppinglist.models.ShoppingIdWithTitle
import ru.skriplenok.shoppinglist.repositories.ProductRepository
import ru.skriplenok.shoppinglist.repositories.ShoppingRepository

abstract class CreatorState(
    protected val shoppingRepository: ShoppingRepository,
    protected val productRepository: ProductRepository
) {

    abstract fun shoppingSave(creatorModel: CreatorModel)

    abstract fun setTitleAndProductList(
        shoppingIdWithTitle: ShoppingIdWithTitle?,
        creatorModel: CreatorModel
    )

    protected fun setShoppingId(shoppingId: Int, productList: List<ProductModel>) {
        for (item in productList) {
            item.product.shoppingId = shoppingId
        }
    }
}