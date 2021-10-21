package com.ldept.shoppinglist.database

import com.ldept.shoppinglist.database.entities.ShoppingItem
import com.ldept.shoppinglist.database.entities.ShoppingList
import com.ldept.shoppinglist.database.entities.relations.ShoppingListWithItems
import kotlinx.coroutines.flow.Flow

class ShoppingListRepository(private val shoppingListDao : ShoppingListDAO) {
    val shoppingLists : Flow<List<ShoppingList>> = shoppingListDao.getLists()

    suspend fun insert(shoppingList : ShoppingList){
        shoppingListDao.insert(shoppingList)
    }

    suspend fun update(shoppingList: ShoppingList){
        shoppingListDao.update(shoppingList)
    }

    suspend fun delete(shoppingList: ShoppingList){
        shoppingListDao.delete(shoppingList)
    }

    fun getShoppingListWithItems(shoppingList: ShoppingList) : Flow<ShoppingListWithItems> {
        return shoppingListDao.getShoppingListWithItems(shoppingListId = shoppingList.shoppingListId)
    }

    suspend fun insert(shoppingItem: ShoppingItem){
        shoppingListDao.insert(shoppingItem)
    }

}