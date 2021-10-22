package com.ldept.shoppinglist.ui.archived_shopping_lists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ldept.shoppinglist.database.entities.ShoppingList
import com.ldept.shoppinglist.databinding.ItemShoppingListBinding

class ArchivedShoppingListsAdapter(
    private val listener: OnItemClickListener
) : ListAdapter<ShoppingList, ArchivedShoppingListsAdapter.ShoppingListViewHolder>(DiffCallback()) {

    interface OnItemClickListener {
        fun onItemClick(shoppingList: ShoppingList)
    }

    inner class ShoppingListViewHolder(
        private val binding: ItemShoppingListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    // It's possible to click a deleted item before an animation finishes
                    // that's why we have to check if the item we clicked is valid or not
                    if(adapterPosition != RecyclerView.NO_POSITION){
                        val shoppingList = getItem(adapterPosition)
                        listener.onItemClick(shoppingList)
                    }
                }
            }
        }

        fun bind(shoppingList: ShoppingList) {
            binding.apply {
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