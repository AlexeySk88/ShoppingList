package ru.skriplenok.shoppinglist

import android.app.Application
import kotlinx.coroutines.runBlocking
import ru.skriplenok.shoppinglist.helpers.QuantityTypes
import ru.skriplenok.shoppinglist.injection.components.AppComponent
import ru.skriplenok.shoppinglist.injection.components.DaggerAppComponent
import ru.skriplenok.shoppinglist.injection.modules.RoomModule
import ru.skriplenok.shoppinglist.repositories.dao.ProductTypeDao
import javax.inject.Inject

class App: Application() {

    @Inject
    lateinit var productTypeDao: ProductTypeDao

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().roomModule(RoomModule(applicationContext)).build()
        appComponent.inject(this)

        setQuantityTypes()
    }

    private fun setQuantityTypes() = runBlocking {
        val productTypes = productTypeDao.getAll()
        QuantityTypes.setInstance(productTypes)
    }

    companion object {
        lateinit var appComponent: AppComponent
            private set
    }
}