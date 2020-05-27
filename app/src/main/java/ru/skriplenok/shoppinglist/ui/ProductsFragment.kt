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
import ru.skriplenok.shoppinglist.R
import ru.skriplenok.shoppinglist.databinding.ProductsFragmentBinding
import ru.skriplenok.shoppinglist.helpers.Constants
import ru.skriplenok.shoppinglist.injection.components.DaggerProductFragmentComponent
import ru.skriplenok.shoppinglist.injection.modules.ProductToolbarModule
import ru.skriplenok.shoppinglist.injection.modules.RoomModule
import ru.skriplenok.shoppinglist.services.ProductToolbar
import ru.skriplenok.shoppinglist.viewmodel.ProductsViewModel
import javax.inject.Inject

class ProductsFragment: Fragment() {

    @Inject
    lateinit var viewModel: ProductsViewModel
    @Inject
    lateinit var productToolbar: ProductToolbar
    private var shoppingId: Int? = null
    private var shoppingTitle: String? = null
    private val toolbarMenuSelected: MutableLiveData<ProductToolbar.ItemMenu> = MutableLiveData()
    private lateinit var navController: NavController;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setArguments()
        val binding = getBinding()
        injectComponents(binding.includeToolbar.toolbar)
        setBindings(savedInstanceState, binding)
        return inflater.inflate(R.layout.products_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    private fun getBinding(): ProductsFragmentBinding {
        return DataBindingUtil.setContentView(requireActivity(), R.layout.products_fragment)
    }

    private fun setArguments() {
        shoppingId = arguments?.getInt(Constants.SHOPPING_ID.value)
        shoppingTitle = arguments?.getString(Constants.SHOPPING_TITLE.value)
    }

    private fun injectComponents(toolbar: Toolbar) {
        DaggerProductFragmentComponent.builder()
            .roomModule(RoomModule(requireContext()))
            .productToolbarModule(ProductToolbarModule(toolbar, shoppingTitle,
                toolbarMenuSelected))
            .build()
            .inject(this)
    }
    private fun setBindings(savedInstanceState: Bundle?, binding: ProductsFragmentBinding) {
        viewModel.init(shoppingId!!)
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