package ru.skriplenok.shoppinglist.viewmodel

import android.content.Context
import androidx.fragment.app.Fragment
import ru.skriplenok.shoppinglist.repositories.ProductTypeRepository
import ru.skriplenok.shoppinglist.repositories.RoomAppDatabase
import ru.skriplenok.shoppinglist.viewmodel.factories.CreatorViewModelFactory

object InjectorViewModel {

    private fun getTypeRepository(context: Context): ProductTypeRepository  {
        val dao = RoomAppDatabase.getDatabase(context).productTypeDao()
        return ProductTypeRepository.getInstance(dao)
    }

    fun provideCreatorViewModel(fragment: Fragment): CreatorViewModelFactory {
        val typeRepository = getTypeRepository(fragment.requireContext())
        return CreatorViewModelFactory(typeRepository, fragment.requireContext(), fragment)
    }
}