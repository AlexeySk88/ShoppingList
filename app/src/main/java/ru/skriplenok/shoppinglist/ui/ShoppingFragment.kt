package ru.skriplenok.shoppinglist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.shopping_fragment.*
import ru.skriplenok.shoppinglist.R
import ru.skriplenok.shoppinglist.databinding.ShoppingFragmentBinding
import ru.skriplenok.shoppinglist.viewmodel.ShoppingViewModel

class ShoppingFragment: Fragment() {

    private lateinit var viewModel: ShoppingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setBindings(savedInstanceState)
        return inflater.inflate(R.layout.shopping_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setBindings(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(ShoppingViewModel::class.java)
        DataBindingUtil.setContentView<ShoppingFragmentBinding>(activity!!, R.layout.shopping_fragment).apply {
            model = viewModel
        }
        if(savedInstanceState == null) {
            viewModel.init()
        }
        setupListUpdate()
    }

    private fun setupListUpdate() {
        viewModel.loading.set(View.VISIBLE)
        val dataList = viewModel.getList()
        viewModel.setModelInAdapter(dataList)
        viewModel.loading.set(View.GONE)
    }
}