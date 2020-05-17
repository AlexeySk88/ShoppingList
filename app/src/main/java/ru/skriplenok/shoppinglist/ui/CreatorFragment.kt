package ru.skriplenok.shoppinglist.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import ru.skriplenok.shoppinglist.R
import ru.skriplenok.shoppinglist.databinding.CreatorFragmentBinding
import ru.skriplenok.shoppinglist.viewmodel.CreatorViewModel
import ru.skriplenok.shoppinglist.viewmodel.InjectorViewModel

class CreatorFragment: Fragment() {

    private lateinit var navController: NavController;
    private val viewModel by viewModels<CreatorViewModel> {
        InjectorViewModel.provideCreatorViewModel(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setBinding(savedInstanceState)
        return inflater.inflate(R.layout.creator_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        setHasOptionsMenu(true) // для onCreateOptionsMenu
        activity?.title = "Создать новый список"
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.creator_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.toolbarClose -> navController.popBackStack()
            R.id.toolbarSave -> viewModel.onClickShoppingSave()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setBinding(savedInstanceState: Bundle?) {
        val binding =
            DataBindingUtil.setContentView<CreatorFragmentBinding>(requireActivity(), R.layout.creator_fragment)

        binding.model = viewModel
        setupToastMessage()
    }

    private fun setupToastMessage() {
        viewModel.toastMessage.observe(viewLifecycleOwner, Observer {
            if (it !== null) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })
    }
}