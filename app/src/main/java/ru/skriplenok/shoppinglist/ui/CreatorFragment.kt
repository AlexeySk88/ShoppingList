package ru.skriplenok.shoppinglist.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import ru.skriplenok.shoppinglist.App
import ru.skriplenok.shoppinglist.R
import ru.skriplenok.shoppinglist.databinding.CreatorFragmentBinding
import ru.skriplenok.shoppinglist.viewmodel.CreatorViewModel
import javax.inject.Inject

class CreatorFragment: Fragment() {

    @Inject
    lateinit var viewModel: CreatorViewModel
    private lateinit var navController: NavController;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        App.appComponent.inject(this)
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

        viewModel.setSpinnerAdapter(requireContext())
        binding.model = viewModel
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.toastMessage.observe(viewLifecycleOwner, Observer {
            if (it !== null) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.onClose.observe(viewLifecycleOwner, Observer {
            if (it) {
                navController.popBackStack()
            }
        })
    }
}