package ru.skriplenok.shoppinglist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import ru.skriplenok.shoppinglist.BR
import ru.skriplenok.shoppinglist.models.ShoppingModel
import ru.skriplenok.shoppinglist.viewmodel.ShoppingViewModel

class ShoppingAdapter(
    @LayoutRes private val layoutId: Int,
    private val viewModel: ShoppingViewModel
): RecyclerView.Adapter<ShoppingAdapter.ViewHolder>() {

    var mDataList: List<ShoppingModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemInflater = LayoutInflater.from(parent.context)
        val dataBinding: ViewDataBinding = DataBindingUtil.inflate(itemInflater, viewType, parent, false)

        return ViewHolder(dataBinding)
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(viewModel, position)
    }

    override fun getItemViewType(position: Int): Int {
        return layoutId
    }

    class ViewHolder(private val binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: ShoppingViewModel, position: Int) {
            binding.setVariable(BR.position, position)
            binding.setVariable(BR.model, viewModel)
            binding.executePendingBindings()


//            (dataBinding as ShoppingCellBinding).data = model
//
//            itemView.apply {
//                setOnClickListener{
//                    clickListener.onItemClicked(model)
//                }
//                setOnLongClickListener{
//                    clickListener.onItemLongClicked(model)
//                    true
//                }
//            }
        }
    }
}