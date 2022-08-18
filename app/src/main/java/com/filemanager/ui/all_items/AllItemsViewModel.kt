package com.filemanager.ui.all_items

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.filemanager.repository.db.item.Item
import com.filemanager.repository.ItemRepository

class AllItemsViewModel(private val repository: ItemRepository, context: Context) : ViewModel() {

    fun allItems(parentID: String): LiveData<List<Item>> = repository.allItems(parentID)

    fun insertItem(item: Item) {
        repository.insertItem(item)
    }

    class ItemsViewModelFactory(
        private val repository: ItemRepository,
        private val context: Context
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AllItemsViewModel::class.java)) {
                return AllItemsViewModel(repository, context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}