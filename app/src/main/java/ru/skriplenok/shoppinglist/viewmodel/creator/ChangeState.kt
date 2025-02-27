package ru.skriplenok.shoppinglist.viewmodel.creator

import kotlinx.coroutines.runBlocking
import ru.skriplenok.shoppinglist.helpers.Converters
import ru.skriplenok.shoppinglist.models.CreatorModel
import ru.skriplenok.shoppinglist.models.ProductModel
import ru.skriplenok.shoppinglist.models.ShoppingIdWithTitle
import ru.skriplenok.shoppinglist.repositories.ProductRepository
import ru.skriplenok.shoppinglist.repositories.ShoppingRepository

class ChangeState(
    shoppingRepository: ShoppingRepository,
    productRepository: ProductRepository
): CreatorState(shoppingRepository, productRepository) {

    private var shoppingIdWithTitle: ShoppingIdWithTitle? = null
    private var initialProductMap: MutableMap<Int, ProductModel>? = null

    private val insertList: MutableList<ProductModel> = mutableListOf()
    private val updateList: MutableList<ProductModel> = mutableListOf()
    private val deleteList: MutableList<ProductModel> = mutableListOf()

    // Чтобы shoppingSave выполнился успешно, перед ним обязательно
    // должен быть вызван метод setTitleAndProductList
    override fun shoppingSave(creatorModel: CreatorModel) {
        titleCompareAndSave(creatorModel.title ?: "")
        productListCompareAndSave(creatorModel.productList)
    }

    private fun titleCompareAndSave(newTitle: String) = shoppingIdWithTitle?.let {
        if (it.title !== newTitle) {
            runBlocking {
                shoppingRepository.updateTitleById(it.id, newTitle)
            }
        }
    }

    private fun productListCompareAndSave(productList: List<ProductModel>) {
        initialProductMap?.let {
            for (productModel in productList) {
                insertOrUpdateList(productModel)
            }
            deleteList.addAll(Converters.productModelMapToList(it))
        }
        saveProductList()
    }

    private fun insertOrUpdateList(productModel: ProductModel) {
        val key = productModel.id
        if (initialProductMap!!.containsKey(key)) {
            if (productModel != initialProductMap!![key]) {
                updateList.add(productModel)
            }
            initialProductMap!!.remove(key)
        } else {
            insertList.add(productModel)
        }
    }

    private fun saveProductList() = shoppingIdWithTitle?.let {
        runBlocking {
            if (insertList.size > 0) {
                setShoppingId(it.id, insertList)
                productRepository.insertAll(insertList)
            }
            if (updateList.size > 0) {
                productRepository.updateAll(updateList)
            }
            if (deleteList.size > 0) {
                productRepository.deleteAll(deleteList)
            }
        }
    }

    override fun setTitleAndProductList(
        shoppingIdWithTitle: ShoppingIdWithTitle?,
        creatorModel: CreatorModel
    ) {
        shoppingIdWithTitle?.let {
            creatorModel.title = shoppingIdWithTitle.title.toString()
            runBlocking {
                creatorModel.productList.addAll(productRepository.getByShoppingId(shoppingIdWithTitle.id))
            }
        }
        this.shoppingIdWithTitle = shoppingIdWithTitle
        initialProductMap = Converters.productModelListToMap(creatorModel.productList).toMutableMap()
    }
}