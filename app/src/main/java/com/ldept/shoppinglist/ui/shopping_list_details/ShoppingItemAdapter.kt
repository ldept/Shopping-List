package com.ldept.shoppinglist.ui.shopping_list_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ldept.shoppinglist.database.entities.ShoppingItem
import com.ldept.shoppinglist.databinding.ItemGroceryBinding

class ShoppingItemsAdapter(
    private val listener: OnItemClickListener
) : ListAdapter<ShoppingItem, ShoppingItemsAdapter.ShoppingItemViewHolder>(DiffCallback()) {

    interface OnItemClickListener {
        fun onItemClick(shoppingItem: ShoppingItem)
    }

    inner class ShoppingItemViewHolder(
        private val binding: ItemGroceryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    // It's possible to click a deleted item before an animation finishes
                    // that's why we have to check if the item we clicked is valid or not
                    if(adapterPosition != RecyclerView.NO_POSITION){
                        val shoppingItem = getItem(adapterPosition)
                        listener.onItemClick(shoppingItem)
                    }
                }
            }
        }

        fun bind(shoppingItem: ShoppingItem) {
            binding.apply {
                groceryTitle.text = shoppingItem.name
                val quantityText = "x${shoppingItem.quantity}"
                groceryQuantity.text = quantityText
            }
        }


    }

    class DiffCallback : DiffUtil.ItemCallback<ShoppingItem>() {
        override fun areContentsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean =
            oldItem == newItem

        override fun areItemsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean =
            oldItem.itemId == newItem.itemId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemViewHolder {
        val binding = ItemGroceryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ShoppingItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShoppingItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

}