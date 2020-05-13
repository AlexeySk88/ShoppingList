package ru.skriplenok.shoppinglist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import ru.skriplenok.shoppinglist.BR
import ru.skriplenok.shoppinglist.viewmodel.ProductCellViewModel

class ProductsAdapter(
    @LayoutRes private val layoutId: Int,
    private val viewModel: ProductCellViewModel
): RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemInflater = LayoutInflater.from(parent.context)
        val dataBinding: ViewDataBinding = DataBindingUtil.inflate(itemInflater, viewType, parent, false)
        return ViewHolder(dataBinding)
    }

    override fun getItemCount(): Int = viewModel.itemCount()

    override fun getItemViewType(position: Int): Int = layoutId

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(viewModel, position)

    class ViewHolder(private val binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: ProductCellViewModel, position: Int) {
            binding.setVariable(BR.mod, viewModel)
            binding.setVariable(BR.pos, position)
            binding.executePendingBindings()
        }
    }
}