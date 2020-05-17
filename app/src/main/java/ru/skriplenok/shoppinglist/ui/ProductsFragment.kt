package ru.skriplenok.shoppinglist.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import ru.skriplenok.shoppinglist.R
import ru.skriplenok.shoppinglist.databinding.ProductsFragmentBinding
import ru.skriplenok.shoppinglist.helpers.Constants
import ru.skriplenok.shoppinglist.viewmodel.InjectorViewModel
import ru.skriplenok.shoppinglist.viewmodel.ProductsViewModel

class ProductsFragment: Fragment() {

    private var shoppingId: Int? = null
    private var shoppingTitle: String? = null
    private val viewModel by viewModels<ProductsViewModel> {
        InjectorViewModel.provideProductViewModel(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setBindings(savedInstanceState)
        return inflater.inflate(R.layout.products_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = shoppingTitle
    }

    private fun setBindings(savedInstanceState: Bundle?) {
        val binding =
            DataBindingUtil.setContentView<ProductsFragmentBinding>(activity!!, R.layout.products_fragment)

        setArguments()
        viewModel.init(shoppingId!!)
        binding.model = viewModel
        setupListUpdate()
    }

    private fun setArguments() {
        shoppingId = arguments?.getInt(Constants.SHOPPING_ID.value)
        shoppingTitle = arguments?.getString(Constants.SHOPPING_TITLE.value)
    }

    private fun setupListUpdate() {
        viewModel.loading.set(View.VISIBLE)
        viewModel.setModelInAdapter()
        viewModel.loading.set(View.GONE)
    }
}