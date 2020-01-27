package com.example.baseapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.baseapp.vo.Grocery
import com.example.baseapp.databinding.ListItemGroceryBinding

class GroceryAdapter : RecyclerView.Adapter<GroceryViewHolder>() {

    val list = ArrayList<Grocery>()

    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryViewHolder {
        return GroceryViewHolder(
            ListItemGroceryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GroceryViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    fun submitList(groceries: List<Grocery>) {
        list.clear()
        list.addAll(groceries)
        notifyDataSetChanged()
    }
}

class GroceryViewHolder(private val binding: ListItemGroceryBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Grocery) {
        binding.apply {
            grocery = item
            executePendingBindings()
        }
    }
}

class GroceryDiffCallback : DiffUtil.ItemCallback<Grocery>() {

    override fun areItemsTheSame(oldItem: Grocery, newItem: Grocery) = oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: Grocery, newItem: Grocery) = oldItem == newItem
}
