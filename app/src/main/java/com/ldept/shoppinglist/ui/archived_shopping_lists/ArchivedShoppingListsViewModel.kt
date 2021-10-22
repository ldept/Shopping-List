package com.ldept.shoppinglist.ui.archived_shopping_lists

import androidx.lifecycle.*
import com.ldept.shoppinglist.database.entities.ShoppingList
import com.ldept.shoppinglist.database.ShoppingListRepository
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class ShoppingListsArchivedViewModel(private val repository: ShoppingListRepository) : ViewModel() {
    val archivedLists: LiveData<List<ShoppingList>> = repository.archivedLists.asLiveData()

}

class ShoppingListsArchivedViewModelFactory(private val repository: ShoppingListRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShoppingListsArchivedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShoppingListsArchivedViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}