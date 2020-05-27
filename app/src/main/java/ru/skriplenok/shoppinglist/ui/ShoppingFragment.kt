package ru.skriplenok.shoppinglist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.toolbar.view.*
import ru.skriplenok.shoppinglist.R
import ru.skriplenok.shoppinglist.databinding.ShoppingFragmentBinding
import ru.skriplenok.shoppinglist.helpers.Constants
import ru.skriplenok.shoppinglist.injection.components.DaggerShoppingFragmentComponent
import ru.skriplenok.shoppinglist.injection.modules.RoomModule
import ru.skriplenok.shoppinglist.injection.modules.ShoppingToolbarModule
import ru.skriplenok.shoppinglist.injection.modules.ShoppingViewModelModule
import ru.skriplenok.shoppinglist.services.ShoppingToolbar
import ru.skriplenok.shoppinglist.viewmodel.ShoppingViewModel
import javax.inject.Inject

class ShoppingFragment: Fragment() {

    @Inject
    lateinit var viewModel: ShoppingViewModel
    @Inject
    lateinit var shoppingToolbar: ShoppingToolbar
    private lateinit var navController: NavController
    private val itemSelected: MutableLiveData<ShoppingToolbar.ItemMenu> = MutableLiveData()
    private val longClickSelectedCount: MutableLiveData<Int> = MutableLiveData()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding =
            DataBindingUtil.setContentView<ShoppingFragmentBinding>(requireActivity(), R.layout.shopping_fragment)
        DaggerShoppingFragmentComponent.builder()
            .roomModule(RoomModule(requireContext()))
            .shoppingViewModelModule(ShoppingViewModelModule(longClickSelectedCount, itemSelected))
            .shoppingToolbarModule(ShoppingToolbarModule(binding.includeToolbar.toolbar, itemSelected,
                                                         longClickSelectedCount))
            .build()
            .inject(this)
        setBindings(savedInstanceState, binding)
        return inflater.inflate(R.layout.shopping_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    private fun setBindings(savedInstanceState: Bundle?, binding: ShoppingFragmentBinding) {
        setToolbar()
        viewModel.init()
        binding.model = viewModel
        setupListClick()
    }

    private fun setToolbar() {
        itemSelected.observe(viewLifecycleOwner, Observer {
            if (it === ShoppingToolbar.ItemMenu.ADD) {
                navController.navigate(R.id.creatorFragment)
            }
        })
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