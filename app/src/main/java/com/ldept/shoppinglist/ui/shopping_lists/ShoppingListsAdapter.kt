package com.ldept.shoppinglist.ui.shopping_lists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ldept.shoppinglist.database.entities.ShoppingList
import com.ldept.shoppinglist.databinding.ItemShoppingListBinding

class ShoppingListsAdapter : ListAdapter<ShoppingList, ShoppingListsAdapter.ShoppingListViewHolder>(DiffCallback()) {


    class ShoppingListViewHolder(private val binding: ItemShoppingListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(shoppingList: ShoppingList){
            binding.apply{
                shoppingListTitle.text = shoppingList.title
                shoppingListDoneDate.text = shoppingList.createdDateFormatted
            }
        }


    }
    class DiffCallback : DiffUtil.ItemCallback<ShoppingList>() {
        override fun areContentsTheSame(oldItem: ShoppingList, newItem: ShoppingList): Boolean =
            oldItem == newItem

        override fun areItemsTheSame(oldItem: ShoppingList, newItem: ShoppingList): Boolean =
            oldItem.shoppingListId == newItem.shoppingListId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListViewHolder {
        val binding = ItemShoppingListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ShoppingListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShoppingListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

}