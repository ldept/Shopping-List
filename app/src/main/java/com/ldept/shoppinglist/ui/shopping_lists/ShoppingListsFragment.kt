package com.ldept.shoppinglist.ui.shopping_lists

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ldept.shoppinglist.R
import com.ldept.shoppinglist.ShoppingListApp
import com.ldept.shoppinglist.database.ShoppingListDatabase
import com.ldept.shoppinglist.database.ShoppingListRepository
import com.ldept.shoppinglist.database.entities.ShoppingItem
import com.ldept.shoppinglist.database.entities.ShoppingList
import com.ldept.shoppinglist.databinding.FragmentShoppingListsBinding

class ShoppingListsFragment : Fragment() {

    private lateinit var viewModel : ShoppingListsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shopping_lists, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentShoppingListsBinding.bind(view)
        val shoppingListsAdapter = ShoppingListsAdapter()

        binding.apply {
            shoppingListsRecyclerview.apply {
                adapter = shoppingListsAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }
        viewModel = ViewModelProvider(this, ShoppingListsViewModelFactory(
            (activity?.application as ShoppingListApp).repository
        )).get(ShoppingListsViewModel::class.java)

        viewModel.shoppingLists.observe(viewLifecycleOwner) {
            shoppingListsAdapter.submitList(it)
        }


        viewModel.insert(ShoppingList(0, "tytuł"))
    }

}