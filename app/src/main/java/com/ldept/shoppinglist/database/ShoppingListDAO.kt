package com.ldept.shoppinglist.database

import androidx.room.*
import com.ldept.shoppinglist.database.entities.ShoppingItem
import com.ldept.shoppinglist.database.entities.ShoppingList
import com.ldept.shoppinglist.database.entities.relations.ShoppingListWithItems
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingListDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(shoppingList: ShoppingList)

    @Update
    suspend fun update(shoppingList: ShoppingList)

    @Delete
    suspend fun delete(shoppingList: ShoppingList)

    @Query("SELECT * FROM shopping_list_table WHERE isArchived = 0 ORDER BY createdDate DESC")
    fun getLists() : Flow<List<ShoppingList>>

    @Query("SELECT * FROM shopping_list_table WHERE isArchived = 1")
    fun getArchivedLists() : Flow<List<ShoppingList>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ShoppingItem)

    @Transaction
    @Query("SELECT * FROM shopping_list_table WHERE shoppingListId = :shoppingListId")
    fun getShoppingListWithItems(shoppingListId: Long) : Flow<List<ShoppingListWithItems>>
}