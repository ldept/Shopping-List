package com.ldept.shoppinglist.ui.shopping_lists

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.ldept.shoppinglist.R
import com.ldept.shoppinglist.ShoppingListApp
import com.ldept.shoppinglist.database.ShoppingListDatabase
import com.ldept.shoppinglist.database.ShoppingListRepository
import com.ldept.shoppinglist.database.entities.ShoppingItem
import com.ldept.shoppinglist.database.entities.ShoppingList
import com.ldept.shoppinglist.databinding.AddEditGroceryBinding
import com.ldept.shoppinglist.databinding.AddShoppingListBinding
import com.ldept.shoppinglist.databinding.FragmentShoppingListsBinding

class ShoppingListsFragment : Fragment(), ShoppingListsAdapter.OnItemClickListener {

    private lateinit var viewModel: ShoppingListsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.findViewById<TabLayout>(R.id.tab_layout)?.visibility = View.VISIBLE
        return inflater.inflate(R.layout.fragment_shopping_lists, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentShoppingListsBinding.bind(view)
        val shoppingListsAdapter = ShoppingListsAdapter(this)

        binding.apply {
            shoppingListsRecyclerview.apply {
                adapter = shoppingListsAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL))
            }
            addShoppingListFab.setOnClickListener {
                showAddDialog()
            }

        }
        viewModel = ViewModelProvider(
            this, ShoppingListsViewModelFactory(
                (activity?.application as ShoppingListApp).repository
            )
        ).get(ShoppingListsViewModel::class.java)

        viewModel.shoppingLists.observe(viewLifecycleOwner) {
            shoppingListsAdapter.submitList(it)
        }

    }

    override fun onItemClick(shoppingList: ShoppingList) {
        val action =
            ShoppingListsFragmentDirections
                .actionShoppingListsFragmentToShoppingListDetailsFragment2(shoppingList, false)
        findNavController().navigate(action)
    }


    private fun showAddDialog(){
        val dialog = activity?.let { Dialog(it) }
        dialog?.apply {
            setCancelable(true)
            setContentView(R.layout.add_edit_grocery)
            setTitle(getString(R.string.add_new_grocery_list))
        }

        val binding = AddShoppingListBinding.inflate(LayoutInflater.from(context))
        dialog?.setContentView(binding.root)


        binding.apply{
            saveListButton.setOnClickListener {
                val nameText = addShoppingListTitleEdittext.text.toString()
                viewModel.onSaveButtonClicked(nameText)
                dialog?.dismiss()
            }
        }
        dialog?.window?.attributes?.width = WindowManager.LayoutParams.MATCH_PARENT
        dialog?.show()
    }

}