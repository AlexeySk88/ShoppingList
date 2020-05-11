package ru.skriplenok.shoppinglist.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import ru.skriplenok.shoppinglist.R
import ru.skriplenok.shoppinglist.R.id.toolbarAdd
import ru.skriplenok.shoppinglist.databinding.ShoppingFragmentBinding
import ru.skriplenok.shoppinglist.helpers.Arguments
import ru.skriplenok.shoppinglist.viewmodel.ShoppingViewModel

class ShoppingFragment: Fragment() {

    private var mActionMode: ActionMode? = null
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
        setHasOptionsMenu(true) // для onCreateOptionsMenu
        activity?.title = "Список покупок"
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.shopping_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId === toolbarAdd) {
            navController.navigate(R.id.creatorFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBindings(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(ShoppingViewModel::class.java)
        val binding =
            DataBindingUtil.setContentView<ShoppingFragmentBinding>(activity!!, R.layout.shopping_fragment)

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
        setupLongListClick()
    }

    private fun setupListClick() {
        viewModel.selected.observe(viewLifecycleOwner, Observer {
            if (it !== null) {
                val bundle = bundleOf(Arguments.SHOPPING_ARGUMENT.value to it)
                navController.navigate(R.id.productsFragment, bundle)
            }
        })
    }

    private fun setupLongListClick() {
        viewModel.longSelected.observe(viewLifecycleOwner, Observer {
            if ((it !== null) and (mActionMode === null)) {
                (activity as AppCompatActivity).startSupportActionMode(object: ActionMode.Callback {
                    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                        return false
                    }

                    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                        val inflater = mode?.menuInflater
                        inflater?.inflate(R.menu.shopping_menu_long_click, menu)
                        return true
                    }

                    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                        return false
                    }

                    override fun onDestroyActionMode(mode: ActionMode?) {
                        mActionMode = null
                    }
                })
            }
        })
    }
}