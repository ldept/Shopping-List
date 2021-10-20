package com.ldept.shoppinglist.database.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.text.DateFormat

@Parcelize
@Entity(tableName = "shopping_list_table")
data class ShoppingList(
    @PrimaryKey(autoGenerate = true) val shoppingListId: Long = 0,
    val title: String,
    val isArchived: Boolean = false,
    val createdDate: Long = System.currentTimeMillis(),
) : Parcelable {
    val createdDateFormatted : String
        get() = DateFormat.getDateInstance().format(createdDate)
}

