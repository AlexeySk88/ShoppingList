package ru.skriplenok.shoppinglist.ui

import android.os.Bundle
import android.view.*
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
    private var argument: ShoppingModel? = null

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
        activity?.title = argument?.name
    }

    private fun setArgument() {
        argument = arguments?.getParcelable(Arguments.SHOPPING_ARGUMENT.value)
    }

    private fun setBindings(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(ProductsViewModel::class.java)
        val binding = DataBindingUtil.setContentView<ProductsFragmentBinding>(
            activity!!,
            R.layout.products_fragment
        )

        if (savedInstanceState == null) {
            viewModel.init()
        }

        if (argument !== null ) {
            viewModel.fetchData(argument!!.id)
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