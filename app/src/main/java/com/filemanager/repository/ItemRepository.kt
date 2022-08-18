package com.filemanager.repository

import androidx.lifecycle.LiveData
import com.filemanager.repository.db.item.Item
import com.filemanager.repository.db.item.ItemDao

class ItemRepository(
    private var itemDao: ItemDao
) {

    fun allItems(parentID: String): LiveData<List<Item>> = itemDao.getAllItems(parentID)

    fun insertItem(item: Item) {
        itemDao.insertItem(item)
    }

}