package ru.skriplenok.shoppinglist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.toolbar.view.*
import ru.skriplenok.shoppinglist.App
import ru.skriplenok.shoppinglist.R
import ru.skriplenok.shoppinglist.databinding.ProductsFragmentBinding
import ru.skriplenok.shoppinglist.helpers.Constants
import ru.skriplenok.shoppinglist.injection.modules.ProductModule
import ru.skriplenok.shoppinglist.models.ShoppingIdWithTitle
import ru.skriplenok.shoppinglist.ui.toolbars.ProductToolbar
import ru.skriplenok.shoppinglist.viewmodel.ProductsViewModel
import javax.inject.Inject

class ProductsFragment: Fragment() {

    @Inject
    lateinit var viewModel: ProductsViewModel
    @Inject
    lateinit var productToolbar: ProductToolbar
    lateinit var toolbarView: Toolbar
        private set
    lateinit var shoppingIdWithTitle: ShoppingIdWithTitle
        private set
    val toolbarMenuSelected: MutableLiveData<ProductToolbar.ItemMenu> = MutableLiveData()
    private lateinit var navController: NavController;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setArguments()
        val binding = getBinding()
        toolbarView = binding.includeToolbar.toolbar
        App.appComponent.productComponent(ProductModule(this)).inject(this)
        setBindings(savedInstanceState, binding)
        return inflater.inflate(R.layout.products_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    private fun setArguments() {
        shoppingIdWithTitle = arguments?.getParcelable(Constants.SHOPPING_ID_WITH_TITLE.value)!!
    }

    private fun getBinding(): ProductsFragmentBinding {
        return DataBindingUtil.setContentView(requireActivity(), R.layout.products_fragment)
    }

    private fun setBindings(savedInstanceState: Bundle?, binding: ProductsFragmentBinding) {
        binding.model = viewModel
        observedItemSelected()
    }

    private fun observedItemSelected() {
        toolbarMenuSelected.observe(viewLifecycleOwner, Observer {
            if (it == ProductToolbar.ItemMenu.ARROW) {
                navController.popBackStack()
            }
        })
    }
}