package ru.skriplenok.shoppinglist.repositories

import ru.skriplenok.shoppinglist.models.ShoppingModel

class FakeRepositories: Repository {

    override fun fetchShoppingList(): List<ShoppingModel> {
        val mockData = mutableListOf<ShoppingModel>()
        mockData.add(ShoppingModel(1, "Продукты на неделю", "0/15"))
        mockData.add(ShoppingModel(2, "Бытовая химия", "1/3"))
        mockData.add(ShoppingModel(3, "На выходные", "2/4"))

        return mockData
    }
}