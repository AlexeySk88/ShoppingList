package ru.skriplenok.shoppinglist.injection.components

import dagger.Component
import ru.skriplenok.shoppinglist.injection.modules.CreatorToolbarModule
import ru.skriplenok.shoppinglist.injection.modules.CreatorViewModelModule
import ru.skriplenok.shoppinglist.injection.modules.RoomModule
import ru.skriplenok.shoppinglist.ui.CreatorFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [
    CreatorViewModelModule::class,
    CreatorToolbarModule::class,
    RoomModule::class
])
interface CreatorFragmentComponent {

    fun inject(fragment: CreatorFragment)
}