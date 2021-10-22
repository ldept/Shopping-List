package com.ldept.shoppinglist.ui.archived_shopping_lists

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.ldept.shoppinglist.R
import com.ldept.shoppinglist.ShoppingListApp
import com.ldept.shoppinglist.database.entities.ShoppingList
import com.ldept.shoppinglist.databinding.FragmentArchivedShoppingListsBinding

class ArchivedShoppingListsFragment : Fragment(), ArchivedShoppingListsAdapter.OnItemClickListener {

    private lateinit var viewModel: ShoppingListsArchivedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.findViewById<TabLayout>(R.id.tab_layout)?.visibility = View.VISIBLE
        return inflater.inflate(R.layout.fragment_archived_shopping_lists, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentArchivedShoppingListsBinding.bind(view)
        val shoppingListsAdapter = ArchivedShoppingListsAdapter(this)

        binding.apply {
            archivedShoppingListsRecyclerview.apply {
                adapter = shoppingListsAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL))
            }
        }
        viewModel = ViewModelProvider(
            this, ShoppingListsArchivedViewModelFactory(
                (activity?.application as ShoppingListApp).repository
            )
        ).get(ShoppingListsArchivedViewModel::class.java)

        viewModel.archivedLists.observe(viewLifecycleOwner) {
            shoppingListsAdapter.submitList(it)
        }
    }

    override fun onItemClick(shoppingList: ShoppingList) {
        val action =
            ArchivedShoppingListsFragmentDirections
                .actionShoppingListsArchivedFragmentToShoppingListDetailsFragment2(shoppingList, true)
        findNavController().navigate(action)
    }

}