package ru.skriplenok.shoppinglist

import android.app.Application
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.skriplenok.shoppinglist.helpers.QuantityTypes
import ru.skriplenok.shoppinglist.injection.components.AppComponent
import ru.skriplenok.shoppinglist.injection.components.DaggerAppComponent
import ru.skriplenok.shoppinglist.injection.modules.RoomModule
import ru.skriplenok.shoppinglist.repositories.dto.ProductTypeDto

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().roomModule(RoomModule(applicationContext)).build()
        setQuantityTypes()
    }

    private fun setQuantityTypes() {
        GlobalScope.launch {
            val productTypes = appComponent.getProductTypeDao().getAll()
            QuantityTypes.setInstance(productTypes)
        }
    }

    companion object {
        lateinit var appComponent: AppComponent
            private set
    }
}