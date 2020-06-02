package ru.skriplenok.shoppinglist.injection.components

import dagger.Subcomponent
import ru.skriplenok.shoppinglist.injection.modules.CreatorModule
import ru.skriplenok.shoppinglist.injection.scopes.CreatorScope
import ru.skriplenok.shoppinglist.ui.CreatorFragment

@CreatorScope
@Subcomponent(modules = [CreatorModule::class])
interface CreatorComponent {

    fun inject(creatorFragment: CreatorFragment)
}