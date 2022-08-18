package com.filemanager.repository.db.item

import androidx.lifecycle.LiveData
import androidx.room.*
import org.jetbrains.annotations.NotNull

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertItem(item: Item)

    @Query("SELECT * FROM item WHERE parentID = :parentID")
    fun getAllItems(parentID: String): LiveData<List<Item>>

    @NotNull
    @Query("SELECT * FROM item WHERE itemID = :itemID")
    fun getItemById(itemID: String): Item
}