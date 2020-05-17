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
import ru.skriplenok.shoppinglist.helpers.Constants
import ru.skriplenok.shoppinglist.viewmodel.ProductsViewModel

class ProductsFragment: Fragment() {

    private lateinit var viewModel: ProductsViewModel
    private var shoppingId: Int? = null
    private var shoppingTitle: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setArgument()
        setBindings(savedInstanceState)
        return inflater.inflate(R.layout.products_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = shoppingTitle
    }

    private fun setArgument() {
        shoppingId = arguments?.getInt(Constants.SHOPPING_ID.value)
        shoppingTitle = arguments?.getString(Constants.SHOPPING_TITLE.value)
    }

    private fun setBindings(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(ProductsViewModel::class.java)
        val binding =
            DataBindingUtil.setContentView<ProductsFragmentBinding>(activity!!, R.layout.products_fragment)

        if (savedInstanceState == null) {
            viewModel.init()
        }

        if (shoppingId !== null ) {
            viewModel.fetchData(shoppingId!!)
        }
        binding.model = viewModel
        setupListUpdate()
    }

    private fun setupListUpdate() {
        viewModel.loading.set(View.VISIBLE)
        viewModel.setModelInAdapter()
        viewModel.loading.set(View.GONE)
    }
}