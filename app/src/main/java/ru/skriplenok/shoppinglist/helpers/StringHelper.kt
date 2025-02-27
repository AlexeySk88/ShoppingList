package ru.skriplenok.shoppinglist.helpers

object StringHelper {

    fun getQuantity(quantity: String, type: String) = "$quantity $type"

    fun getShoppingFraction (numerator: String, denominator: String) = "$numerator/$denominator"
}