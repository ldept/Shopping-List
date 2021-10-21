package com.ldept.shoppinglist.ui.shopping_list_details

import androidx.lifecycle.*
import com.ldept.shoppinglist.database.ShoppingListRepository
import com.ldept.shoppinglist.database.entities.ShoppingItem
import com.ldept.shoppinglist.database.entities.ShoppingList
import com.ldept.shoppinglist.database.entities.relations.ShoppingListWithItems
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class ShoppingListDetailsViewModel(
    private val repository: ShoppingListRepository,
    private val shoppingList: ShoppingList
) : ViewModel() {

    val shoppingItems : LiveData<ShoppingListWithItems> =
        repository.getShoppingListWithItems(shoppingList).asLiveData()

    fun insert(shoppingList: ShoppingList) = viewModelScope.launch {
        repository.insert(shoppingList)
    }

    fun update(shoppingList: ShoppingList) = viewModelScope.launch {
        repository.update(shoppingList)
    }

    fun delete(shoppingList: ShoppingList) = viewModelScope.launch {
        repository.delete(shoppingList)
    }
    fun onShoppingItemSelected(shoppingItem: ShoppingItem){

    }

    fun onAddButtonClicked(nameText : String, quantity : Int) = viewModelScope.launch {

        val shoppingItem = ShoppingItem(
            0,
            nameText,
            quantity,
            shoppingListId = shoppingList.shoppingListId
        )
        repository.insert(shoppingItem)
    }

}

class ShoppingListDetailsViewModelFactory(
    private val repository: ShoppingListRepository,
    private val shoppingList: ShoppingList
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ShoppingListDetailsViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return  ShoppingListDetailsViewModel(repository, shoppingList) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}