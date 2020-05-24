package ru.skriplenok.shoppinglist

import android.app.Application
import ru.skriplenok.shoppinglist.injection.components.AppComponent
import ru.skriplenok.shoppinglist.injection.components.DaggerAppComponent
import ru.skriplenok.shoppinglist.injection.modules.RoomModule

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().roomModule(RoomModule(applicationContext)).build()
    }

    companion object {
        lateinit var appComponent: AppComponent
            private set
    }
}