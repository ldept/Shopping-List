package com.ldept.shoppinglist.database

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.*
import org.junit.Test

class ShoppingListDatabaseTest {
    @Test
    fun databaseSingletonTest(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        val database1 = ShoppingListDatabase.getDatabase(context)
        val database2 = ShoppingListDatabase.getDatabase(context)
        assertSame(database1, database2)
    }

}