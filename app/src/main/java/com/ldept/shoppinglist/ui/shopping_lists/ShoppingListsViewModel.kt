package com.ldept.shoppinglist.ui.shopping_lists

import androidx.lifecycle.*
import com.ldept.shoppinglist.database.entities.ShoppingList
import com.ldept.shoppinglist.database.ShoppingListRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class ShoppingListsViewModel(private val repository: ShoppingListRepository) : ViewModel() {
    val shoppingLists: LiveData<List<ShoppingList>> = repository.shoppingLists.asLiveData()

    fun onSaveButtonClicked(name: String) = viewModelScope.launch {
        repository.insert(ShoppingList(0,name))
    }

}

class ShoppingListsViewModelFactory(private val repository: ShoppingListRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShoppingListsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShoppingListsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}