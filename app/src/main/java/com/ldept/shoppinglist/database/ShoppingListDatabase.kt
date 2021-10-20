package com.ldept.shoppinglist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ldept.shoppinglist.database.entities.ShoppingItem
import com.ldept.shoppinglist.database.entities.ShoppingList

@Database(entities = [ShoppingList::class, ShoppingItem::class], version = 1)
abstract class ShoppingListDatabase : RoomDatabase() {

    abstract fun shoppingListDao() : ShoppingListDAO

    companion object {
        @Volatile
        private var INSTANCE: ShoppingListDatabase? = null

        fun getDatabase(context: Context): ShoppingListDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ShoppingListDatabase::class.java,
                    "shopping_list_database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}