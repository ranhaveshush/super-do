package com.example.baseapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.baseapp.databinding.ListItemGroceryBinding
import com.example.baseapp.vo.Grocery

class GroceryAdapter : ListAdapter<Grocery, GroceryViewHolder>(GroceryDiffCallback()) {

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
        val item = getItem(position)
        holder.bind(item)
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

    override fun areItemsTheSame(oldItem: Grocery, newItem: Grocery) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Grocery, newItem: Grocery) =
        oldItem.name == newItem.name
                && oldItem.weight == newItem.weight
                && oldItem.color == newItem.color
}
