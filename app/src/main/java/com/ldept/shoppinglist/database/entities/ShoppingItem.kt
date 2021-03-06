package com.ldept.shoppinglist.database.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "shopping_item_table")
data class ShoppingItem(
    @PrimaryKey(autoGenerate = true) val itemId : Long,
    val name: String,
    val quantity: Int = 1,
    val isChecked: Boolean = false,
    val shoppingListId: Long,
) : Parcelable