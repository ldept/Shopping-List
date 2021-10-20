package com.ldept.shoppinglist.database.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.ldept.shoppinglist.database.entities.ShoppingItem
import com.ldept.shoppinglist.database.entities.ShoppingList

data class ShoppingListWithItems (
    @Embedded val shoppingList : ShoppingList,
    @Relation(
        parentColumn = "shoppingListId",
        entityColumn = "shoppingListId"
    )
    val items: List<ShoppingItem>?
)