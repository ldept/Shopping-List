package com.ldept.shoppinglist.ui.shopping_list_details

import android.app.Dialog
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.ldept.shoppinglist.R
import com.ldept.shoppinglist.ShoppingListApp
import com.ldept.shoppinglist.database.entities.ShoppingItem
import com.ldept.shoppinglist.database.entities.ShoppingList
import com.ldept.shoppinglist.databinding.AddEditGroceryBinding
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
        // Hide tabs
        activity?.findViewById<TabLayout>(R.id.tab_layout)?.visibility = View.GONE
        return inflater.inflate(R.layout.fragment_shopping_lists_details, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shoppingList = args.shoppingList
        isListArchived = args.isListArchived

        val binding = FragmentShoppingListsDetailsBinding.bind(view)
        val shoppingItemsAdapter = ShoppingItemsAdapter(this, isListArchived)


        binding.apply {
            groceriesRecyclerview.apply {
                adapter = shoppingItemsAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            }

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
            archiveListButton.setOnClickListener {
                viewModel.update(shoppingList.copy(isArchived = !shoppingList.isArchived))
                findNavController().navigateUp()
            }

            addGroceryFab.setOnClickListener {
                showAddEditDialog(null)
            }

            if(isListArchived){
                editTitleButton.visibility = View.INVISIBLE
                addGroceryFab.visibility = View.INVISIBLE
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
        if(!isListArchived)
            showAddEditDialog(shoppingItem)
    }

    override fun onItemCheckboxClick(shoppingItem: ShoppingItem, isChecked: Boolean) {
        if(!isListArchived)
            viewModel.onItemCheckboxSelected(shoppingItem, isChecked)
    }

    private fun showAddEditDialog(shoppingItem: ShoppingItem?){
        val dialog = activity?.let { Dialog(it) }
        dialog?.apply {
            setCancelable(true)
            setContentView(R.layout.add_edit_grocery)
            val title = if (shoppingItem != null)
                getString(R.string.edit_grocery) else getString(R.string.add_new_grocery)
            setTitle(title)
        }

        val binding = AddEditGroceryBinding.inflate(LayoutInflater.from(context))
        dialog?.setContentView(binding.root)


        binding.apply{
            if (shoppingItem != null){
                addGroceryTitleEdittext.setText(shoppingItem.name.toString())
                addGroceryQuantityEdittext.setText(shoppingItem.quantity.toString())
                deleteGroceryButton.visibility = View.VISIBLE
            }
            saveGroceryButton.setOnClickListener {
                val nameText = addGroceryTitleEdittext.text.toString()
                val quantityText = addGroceryQuantityEdittext.text.toString()
                val quantity = if (quantityText == "") 1 else quantityText.toInt()
                if(shoppingItem != null)
                    viewModel.onSaveButtonClicked(shoppingItem.copy(name = nameText,quantity = quantity))
                else
                    viewModel.onSaveButtonClicked(nameText, quantity)
                dialog?.dismiss()
            }
            deleteGroceryButton.setOnClickListener {
                shoppingItem?.let { item -> viewModel.onDeleteButtonClicked(item) }
                dialog?.dismiss()
            }
        }
        dialog?.window?.attributes?.width = WindowManager.LayoutParams.MATCH_PARENT
        dialog?.show()
    }

}