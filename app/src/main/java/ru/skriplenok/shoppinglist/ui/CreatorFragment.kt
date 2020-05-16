package ru.skriplenok.shoppinglist.ui

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import ru.skriplenok.shoppinglist.R
import ru.skriplenok.shoppinglist.databinding.CreatorFragmentBinding
import ru.skriplenok.shoppinglist.viewmodel.CreatorViewModel

class CreatorFragment: Fragment() {

    private lateinit var viewModel: CreatorViewModel
    private lateinit var navController: NavController;

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
        if (item.itemId == R.id.toolbarClose) {
           navController.popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBinding(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(CreatorViewModel::class.java)
        val binding =
            DataBindingUtil.setContentView<CreatorFragmentBinding>(activity!!, R.layout.creator_fragment)

        viewModel.init(requireContext())
        binding.model = viewModel
        viewModel.setModelInAdapter()
    }
}