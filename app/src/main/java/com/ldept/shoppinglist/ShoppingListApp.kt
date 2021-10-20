package com.ldept.shoppinglist

import android.app.Application
import com.ldept.shoppinglist.database.ShoppingListDatabase
import com.ldept.shoppinglist.database.ShoppingListRepository

class ShoppingListApp : Application() {
    val database by lazy { ShoppingListDatabase.getDatabase(this) }
    val repository by lazy { ShoppingListRepository(database.shoppingListDao()) }

}