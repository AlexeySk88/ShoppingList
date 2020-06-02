package ru.skriplenok.shoppinglist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import ru.skriplenok.shoppinglist.databinding.CreatorFragmentBinding
import ru.skriplenok.shoppinglist.injection.modules.CreatorModule
import ru.skriplenok.shoppinglist.services.CreatorToolbar
import ru.skriplenok.shoppinglist.viewmodel.CreatorViewModel
import javax.inject.Inject

class CreatorFragment: Fragment() {

    @Inject
    lateinit var viewModel: CreatorViewModel
    @Inject
    lateinit var creatorToolbar: CreatorToolbar
    lateinit var toolbarView: Toolbar
    val toolbarMenuSelected: MutableLiveData<CreatorToolbar.ItemMenu> = MutableLiveData()
    private lateinit var navController: NavController;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = getBinding()
        toolbarView = binding.includeToolbar.toolbar
        App.appComponent.creatorComponent(CreatorModule(this)).inject(this)
        setBinding(savedInstanceState, binding)
        return inflater.inflate(R.layout.creator_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    private fun getBinding(): CreatorFragmentBinding {
        return DataBindingUtil.setContentView(requireActivity(), R.layout.creator_fragment)
    }

    private fun setBinding(savedInstanceState: Bundle?, binding: CreatorFragmentBinding) {
        viewModel.setSpinnerAdapter(requireContext())
        binding.model = viewModel
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.toastMessage.observe(viewLifecycleOwner, Observer {
            if (it !== null) {
                // TODO закрывать при onPause
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })

        toolbarMenuSelected.observe(viewLifecycleOwner, Observer {
            if (it == CreatorToolbar.ItemMenu.CLOSE) {
                navController.popBackStack()
            }
        })

        viewModel.onClose.observe(viewLifecycleOwner, Observer {
            if (it) {
                navController.popBackStack()
            }
        })
    }
}