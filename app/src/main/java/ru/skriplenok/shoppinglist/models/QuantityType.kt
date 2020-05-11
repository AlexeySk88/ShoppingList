package ru.skriplenok.shoppinglist.models

enum class QuantityType(val value: Quantity) {
    GRAM(Quantity("грамм", "гр.")),
    KILOGRAM(Quantity("килограм", "кг.")),
    MILLILITER(Quantity("миллилитр", "мл.")),
    LITER(Quantity("литр", "л.")),
    THING(Quantity("упаковка", "уп.")),
}

data class Quantity(val name: String, val shortName: String)