package ru.skriplenok.shoppinglist.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.skriplenok.shoppinglist.R
import ru.skriplenok.shoppinglist.helpers.QuantityTypes
import ru.skriplenok.shoppinglist.repositories.RoomAppDatabase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        setQuantityTypes(context)
        return super.onCreateView(name, context, attrs)
    }

    private fun setQuantityTypes(context: Context) {
        GlobalScope.launch {
            val productsType = RoomAppDatabase.getDatabase(context).productTypeDao().getAll()
            QuantityTypes.setInstance(productsType)
        }
    }
}
