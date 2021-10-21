package com.ldept.shoppinglist.ui.shopping_list_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ldept.shoppinglist.R
import com.ldept.shoppinglist.ShoppingListApp
import com.ldept.shoppinglist.database.entities.ShoppingItem
import com.ldept.shoppinglist.database.entities.ShoppingList
import com.ldept.shoppinglist.databinding.FragmentShoppingListsBinding
import com.ldept.shoppinglist.databinding.FragmentShoppingListsDetailsBinding
import com.ldept.shoppinglist.ui.shopping_lists.ShoppingListsAdapter
import com.ldept.shoppinglist.ui.shopping_lists.ShoppingListsViewModel
import com.ldept.shoppinglist.ui.shopping_lists.ShoppingListsViewModelFactory


class ShoppingListDetailsFragment : Fragment(), ShoppingItemsAdapter.OnItemClickListener{

    private lateinit var viewModel: ShoppingListDetailsViewModel
    private val args : ShoppingListDetailsFragmentArgs by navArgs()
    private lateinit var shoppingList : ShoppingList

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shopping_lists_details, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentShoppingListsDetailsBinding.bind(view)
        val shoppingItemsAdapter = ShoppingItemsAdapter(this)

        shoppingList = args.shoppingList

        binding.apply {
            groceriesRecyclerview.apply {
                adapter = shoppingItemsAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            }

            addGroceryButton.setOnClickListener {
                val nameText = addGroceryTitleEdittext.text.toString()
                val quantityText = addGroceryQuantityEdittext.text.toString()
                val quantity = if (quantityText == "") 1 else quantityText.toInt()
                viewModel.onAddButtonClicked(nameText, quantity)
            }

        }
        viewModel = ViewModelProvider(
            this, ShoppingListDetailsViewModelFactory(
                (activity?.application as ShoppingListApp).repository,
                shoppingList
            )
        ).get(ShoppingListDetailsViewModel::class.java)

        viewModel.shoppingItems.observe(viewLifecycleOwner) {
            shoppingItemsAdapter.submitList(it.items)
        }



    }

    override fun onItemClick(shoppingItem: ShoppingItem) {
        // TODO:
    }

    override fun onItemCheckboxClick(shoppingItem: ShoppingItem, isChecked: Boolean) {
        viewModel.onItemCheckboxSelected(shoppingItem, isChecked)
    }

}