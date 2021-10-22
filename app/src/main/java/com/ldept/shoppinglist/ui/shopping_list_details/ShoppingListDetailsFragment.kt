package com.ldept.shoppinglist.ui.shopping_list_details

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
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
    private var isListArchived : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.findViewById<TabLayout>(R.id.tab_layout)?.visibility = View.GONE
        return inflater.inflate(R.layout.fragment_shopping_lists_details, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentShoppingListsDetailsBinding.bind(view)
        val shoppingItemsAdapter = ShoppingItemsAdapter(this)

        shoppingList = args.shoppingList
        isListArchived = args.isListArchived
        binding.apply {
            groceriesRecyclerview.apply {
                adapter = shoppingItemsAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            }

//            addGroceryButton.setOnClickListener {
//                val nameText = addGroceryTitleEdittext.text.toString()
//                val quantityText = addGroceryQuantityEdittext.text.toString()
//                val quantity = if (quantityText == "") 1 else quantityText.toInt()
//                viewModel.onAddButtonClicked(nameText, quantity)
//            }
            shoppingListTitleTextview.text = shoppingList.title
            editTitleButton.setOnClickListener {
                val titleText = shoppingListTitleTextview.text.toString()
                shoppingListTitleEdittext.setText(titleText)
                groupDisplay.visibility = View.INVISIBLE
                groupEditing.visibility = View.VISIBLE
                shoppingListTitleEdittext.requestFocus()
            }
            saveTitleButton.setOnClickListener {
                val titleText = shoppingListTitleEdittext.text.toString()
                groupDisplay.visibility = View.VISIBLE
                groupEditing.visibility = View.GONE
                viewModel.update(shoppingList.copy(title = titleText))
                shoppingListTitleTextview.text = titleText
            }

            if(isListArchived){
                addGroceryFab.visibility = View.GONE
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