package ru.skriplenok.shoppinglist.viewmodel

import android.content.Context
import androidx.fragment.app.Fragment
import ru.skriplenok.shoppinglist.repositories.ProductRepository
import ru.skriplenok.shoppinglist.repositories.RoomAppDatabase
import ru.skriplenok.shoppinglist.repositories.ShoppingRepository
import ru.skriplenok.shoppinglist.viewmodel.factories.CreatorViewModelFactory
import ru.skriplenok.shoppinglist.viewmodel.factories.ProductViewModelFactory
import ru.skriplenok.shoppinglist.viewmodel.factories.ShoppingViewModelFactory

object InjectorViewModel {

    fun provideCreatorViewModel(fragment: Fragment): CreatorViewModelFactory
            = CreatorViewModelFactory(fragment.requireContext(), fragment)

    fun provideShoppingViewModel(fragment: Fragment): ShoppingViewModelFactory {
        val shoppingRepository = getShoppingRepository(fragment.requireContext())
        return ShoppingViewModelFactory(shoppingRepository, fragment)
    }

    private fun getShoppingRepository(context: Context): ShoppingRepository {
        val dao = RoomAppDatabase.getDatabase(context).shoppingDao()
        return ShoppingRepository.getInstance(dao)
    }

    fun provideProductViewModel(fragment: Fragment): ProductViewModelFactory {
        val productRepository = getProductRepository(fragment.requireContext())
        return ProductViewModelFactory(productRepository, fragment)
    }

    private fun getProductRepository(context: Context): ProductRepository {
        val dao = RoomAppDatabase.getDatabase(context).productDao()
        return ProductRepository.getInstance(dao)
    }
}