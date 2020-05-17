package ru.skriplenok.shoppinglist.viewmodel

import android.content.Context
import androidx.fragment.app.Fragment
import ru.skriplenok.shoppinglist.repositories.ProductTypeRepository
import ru.skriplenok.shoppinglist.repositories.RoomAppDatabase
import ru.skriplenok.shoppinglist.repositories.ShoppingRepository
import ru.skriplenok.shoppinglist.viewmodel.factories.CreatorViewModelFactory
import ru.skriplenok.shoppinglist.viewmodel.factories.ShoppingViewModelFactory

object InjectorViewModel {

    private fun getTypeRepository(context: Context): ProductTypeRepository  {
        val dao = RoomAppDatabase.getDatabase(context).productTypeDao()
        return ProductTypeRepository.getInstance(dao)
    }

    private fun getShoppingRepository(context: Context): ShoppingRepository {
        val dao = RoomAppDatabase.getDatabase(context).shoppingDao()
        return ShoppingRepository.getInstance(dao)
    }

    fun provideCreatorViewModel(fragment: Fragment): CreatorViewModelFactory {
        val typeRepository = getTypeRepository(fragment.requireContext())
        return CreatorViewModelFactory(typeRepository, fragment.requireContext(), fragment)
    }

    fun provideShoppingViewModel(fragment: Fragment): ShoppingViewModelFactory {
        val shoppingRepository = getShoppingRepository(fragment.requireContext())
        return ShoppingViewModelFactory(shoppingRepository, fragment)
    }
}