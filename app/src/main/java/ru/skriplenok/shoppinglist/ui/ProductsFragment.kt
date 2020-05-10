package ru.skriplenok.shoppinglist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.skriplenok.shoppinglist.R
import ru.skriplenok.shoppinglist.databinding.ProductsFragmentBinding
import ru.skriplenok.shoppinglist.helpers.Arguments
import ru.skriplenok.shoppinglist.models.ShoppingModel
import ru.skriplenok.shoppinglist.viewmodel.ProductsViewModel

class ProductsFragment: Fragment() {

    private lateinit var viewModel: ProductsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val shoppingModel = getBundle()
        setBindings(savedInstanceState, shoppingModel.id)
        return inflater.inflate(R.layout.products_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun getBundle(): ShoppingModel {
        val argument = arguments?.getParcelable<ShoppingModel>(Arguments.SHOPPING_ARGUMENT.value)
        return argument!! // TODO добавить обратчик null
    }

    private fun setBindings(savedInstanceState: Bundle?, listId: Int) {
        viewModel = ViewModelProvider(this).get(ProductsViewModel::class.java)
        val binding = DataBindingUtil.setContentView<ProductsFragmentBinding>(
            activity!!,
            R.layout.products_fragment
        )

        if (savedInstanceState == null) {
            viewModel.init()
        }

        viewModel.fetchData(listId)
        binding.model = viewModel
        setupListUpdate()
    }

    private fun setupListUpdate() {
        viewModel.loading.set(View.VISIBLE)
        viewModel.setModelInAdapter()
        viewModel.loading.set(View.GONE)
    }
}