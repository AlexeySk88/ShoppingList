package ru.skriplenok.shoppinglist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import ru.skriplenok.shoppinglist.App
import ru.skriplenok.shoppinglist.R
import ru.skriplenok.shoppinglist.databinding.ShoppingFragmentBinding
import ru.skriplenok.shoppinglist.helpers.Constants
import ru.skriplenok.shoppinglist.services.ShoppingToolbar
import ru.skriplenok.shoppinglist.viewmodel.ShoppingViewModel
import javax.inject.Inject

class ShoppingFragment: Fragment() {

    @Inject
    lateinit var viewModel: ShoppingViewModel
    private lateinit var shoppingToolbar: ShoppingToolbar
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        App.appComponent.inject(this)
        setBindings(savedInstanceState)
        return inflater.inflate(R.layout.shopping_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    private fun setBindings(savedInstanceState: Bundle?) {
        val binding =
            DataBindingUtil.setContentView<ShoppingFragmentBinding>(activity!!, R.layout.shopping_fragment)

        setToolbar(binding)
        viewModel.init()
        binding.model = viewModel
        setupListUpdate()
    }

    private fun setToolbar(binding: ShoppingFragmentBinding) {
        shoppingToolbar = ShoppingToolbar(binding.toolbar, viewModel.longClickSelectedCount)
        shoppingToolbar.itemSelected.observe(viewLifecycleOwner, Observer {
            if (it === ShoppingToolbar.ItemMenu.ADD) {
                navController.navigate(R.id.creatorFragment)
            }
        })
        viewModel.setMenuItemClickListener(shoppingToolbar.itemSelected)
    }

    private fun setupListUpdate() {
        setupListClick()
    }

    private fun setupListClick() {
        viewModel.clickSelected.observe(viewLifecycleOwner, Observer {
            if (it !== null) {
                val bundle = bundleOf(
                    Constants.SHOPPING_ID.value to it.shopping.id,
                    Constants.SHOPPING_TITLE.value to it.shopping.name
                )
                navController.navigate(R.id.productsFragment, bundle)
            }
        })
    }
}