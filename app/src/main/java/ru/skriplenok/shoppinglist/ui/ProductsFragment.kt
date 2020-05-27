package ru.skriplenok.shoppinglist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.toolbar.view.*
import ru.skriplenok.shoppinglist.App
import ru.skriplenok.shoppinglist.R
import ru.skriplenok.shoppinglist.databinding.ProductsFragmentBinding
import ru.skriplenok.shoppinglist.helpers.Constants
import ru.skriplenok.shoppinglist.viewmodel.ProductsViewModel
import javax.inject.Inject

class ProductsFragment: Fragment() {

    @Inject
    lateinit var viewModel: ProductsViewModel
    private var shoppingId: Int? = null
    private var shoppingTitle: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        App.appComponent.inject(this)
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

        binding.includeToolbar.toolbar.title = "SIMPLE TEXT"
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