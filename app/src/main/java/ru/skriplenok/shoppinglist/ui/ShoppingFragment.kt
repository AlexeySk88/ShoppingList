package ru.skriplenok.shoppinglist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.toolbar.view.*
import ru.skriplenok.shoppinglist.App
import ru.skriplenok.shoppinglist.R
import ru.skriplenok.shoppinglist.databinding.ShoppingFragmentBinding
import ru.skriplenok.shoppinglist.injection.modules.ShoppingModule
import ru.skriplenok.shoppinglist.ui.toolbars.ShoppingToolbar
import ru.skriplenok.shoppinglist.viewmodel.ShoppingViewModel
import javax.inject.Inject

class ShoppingFragment: Fragment() {

    @Inject
    lateinit var viewModel: ShoppingViewModel
    @Inject
    lateinit var shoppingToolbar: ShoppingToolbar

    lateinit var toolbarView: Toolbar
        private set

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = getBinding()
        toolbarView = binding.includeToolbar.toolbar
        App.appComponent.shoppingComponent(ShoppingModule(this)).inject(this)
        setBindings(savedInstanceState, binding)
        return inflater.inflate(R.layout.shopping_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    private fun getBinding(): ShoppingFragmentBinding {
        return DataBindingUtil.setContentView(requireActivity(), R.layout.shopping_fragment)
    }

    private fun setBindings(savedInstanceState: Bundle?, binding: ShoppingFragmentBinding) {
        binding.model = viewModel
        navigationListener()
    }

    private fun navigationListener() {
        viewModel.navigation.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                navController.navigate(it.first, it.second)
            }
        })
    }
}