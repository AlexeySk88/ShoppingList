package ru.skriplenok.shoppinglist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import ru.skriplenok.shoppinglist.BR
import ru.skriplenok.shoppinglist.viewmodel.ShoppingViewModel

class ShoppingAdapter(
    @LayoutRes private val layoutId: Int,
    private val viewModel: ShoppingViewModel
): RecyclerView.Adapter<ShoppingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemInflater = LayoutInflater.from(parent.context)
        val dataBinding: ViewDataBinding = DataBindingUtil.inflate(itemInflater, viewType, parent, false)

        return ViewHolder(dataBinding)
    }

    override fun getItemCount(): Int = viewModel.countItems

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(viewModel, position)

    override fun getItemViewType(position: Int): Int = layoutId

    class ViewHolder(private val binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: ShoppingViewModel, position: Int) {
            binding.setVariable(BR.position, position)
            binding.setVariable(BR.model, viewModel)
            binding.executePendingBindings()

            itemView.apply {
                setOnClickListener {
                    viewModel.onClick(position)
                }
                setOnLongClickListener {
                    viewModel.onLongClick(position)
                }
            }
        }
    }
}