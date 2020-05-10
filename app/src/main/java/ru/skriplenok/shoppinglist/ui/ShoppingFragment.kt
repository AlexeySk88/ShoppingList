package ru.skriplenok.shoppinglist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import ru.skriplenok.shoppinglist.R
import ru.skriplenok.shoppinglist.databinding.ShoppingFragmentBinding
import ru.skriplenok.shoppinglist.helpers.Arguments
import ru.skriplenok.shoppinglist.viewmodel.ShoppingViewModel

class ShoppingFragment: Fragment() {

    private lateinit var viewModel: ShoppingViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setBindings(savedInstanceState)
        return inflater.inflate(R.layout.shopping_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    private fun setBindings(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(ShoppingViewModel::class.java)
        val binding = DataBindingUtil.setContentView<ShoppingFragmentBinding>(
            activity!!,
            R.layout.shopping_fragment)

        if(savedInstanceState == null) {
            viewModel.init()
        }

        viewModel.fetchData()
        binding.model = viewModel
        setupListUpdate()
    }

    private fun setupListUpdate() {
        viewModel.loading.set(View.VISIBLE)
        viewModel.setModelInAdapter()
        viewModel.loading.set(View.GONE)

        setupListClick()
    }

    private fun setupListClick() {
        viewModel.selected.observe(viewLifecycleOwner, Observer {
            if (it !== null) {
                val bundle = bundleOf(Arguments.SHOPPING_ARGUMENT.value to it)
                navController.navigate(R.id.productsFragment, bundle)
            }
        })
    }
}