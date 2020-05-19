package ru.skriplenok.shoppinglist.viewmodel

interface ProductCellViewModel {

    fun getTitle(position: Int): String?
    fun getSelected(position: Int): Boolean
    fun onSelected(position: Int)
    fun getQuantity(position: Int): String?
    fun getVisible(): Int
    fun itemCount(): Int
}