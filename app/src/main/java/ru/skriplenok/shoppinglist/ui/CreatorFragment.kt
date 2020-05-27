package ru.skriplenok.shoppinglist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.toolbar.view.*
import ru.skriplenok.shoppinglist.R
import ru.skriplenok.shoppinglist.databinding.CreatorFragmentBinding
import ru.skriplenok.shoppinglist.injection.components.DaggerCreatorFragmentComponent
import ru.skriplenok.shoppinglist.injection.modules.CreatorToolbarModule
import ru.skriplenok.shoppinglist.injection.modules.CreatorViewModelModule
import ru.skriplenok.shoppinglist.injection.modules.RoomModule
import ru.skriplenok.shoppinglist.services.CreatorToolbar
import ru.skriplenok.shoppinglist.viewmodel.CreatorViewModel
import javax.inject.Inject

class CreatorFragment: Fragment() {

    @Inject
    lateinit var viewModel: CreatorViewModel
    @Inject
    lateinit var creatorToolbar: CreatorToolbar
    private lateinit var navController: NavController;
    private val itemSelected: MutableLiveData<CreatorToolbar.ItemMenu> = MutableLiveData()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding =
            DataBindingUtil.setContentView<CreatorFragmentBinding>(requireActivity(), R.layout.creator_fragment)
        DaggerCreatorFragmentComponent.builder()
            .roomModule(RoomModule(requireContext()))
            .creatorToolbarModule(CreatorToolbarModule(binding.includeToolbar.toolbar, itemSelected))
            .creatorViewModelModule(CreatorViewModelModule(itemSelected))
            .build()
            .inject(this)

        setBinding(savedInstanceState, binding)
        return inflater.inflate(R.layout.creator_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
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

        itemSelected.observe(viewLifecycleOwner, Observer {
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