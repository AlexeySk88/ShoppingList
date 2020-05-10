package ru.skriplenok.shoppinglist.repositories

import ru.skriplenok.shoppinglist.models.ProductsModel
import ru.skriplenok.shoppinglist.models.ShoppingModel

class FakeRepositories: Repository {

    override fun fetchShoppingList(): List<ShoppingModel> {
        val mockData = mutableListOf<ShoppingModel>()
        mockData.add(ShoppingModel(1, "Продукты на неделю", "0/15"))
        mockData.add(ShoppingModel(2, "Бытовая химия", "1/3"))
        mockData.add(ShoppingModel(3, "На выходные", "2/4"))

        return mockData
    }

    override fun fetchProductList(id: Int): List<ProductsModel> {
        val mockData: List<ProductsModel>

        when(id) {
            1 -> mockData = listOf(
                ProductsModel(1, "Горошек", "1 шт.", true),
                ProductsModel(2, "Докторская колбаса", "1 шт", true),
                ProductsModel(3, "Огурец конс.", "1 шт.", true),
                ProductsModel(10, "Майонез", "1 шт.", true)
            )

            2 -> mockData = listOf(
                ProductsModel(4, "Чай", "1 упаковка", true),
                ProductsModel(5, "Печенье", "1 упаковка", true),
                ProductsModel(6, "Сгущенка", "1 шт.", true)
            )

            3 -> mockData = listOf(
                ProductsModel(7, "Вода", "1 шт.", true),
                ProductsModel(8, "Пельмени", "1 упаковка", true),
                ProductsModel(9, "Соль", "1 шт.", true)
            )

            else -> mockData = listOf(
                ProductsModel(11, "Бананы", "1 кг.", true)
            )
        }

        return mockData
    }
}