package com.filemanager.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.filemanager.R
import com.filemanager.utils.Constants
import com.filemanager.utils.Constants.BASE_URL
import com.filemanager.utils.Constants.PARENT_ID_MAIN
import com.filemanager.repository.db.item.Item
import com.filemanager.repository.db.item.ItemDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

@Database(
    entities = [Item::class],
    version = 1,
    exportSchema = false
)
abstract class FileManagerDB : RoomDatabase() {

    abstract fun itemDao(): ItemDao

    companion object {
        @Volatile
        var INSTANCE: FileManagerDB? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): FileManagerDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FileManagerDB::class.java,
                    context.getString(R.string.file_manager_database)
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(FileManagerDBCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

    private class FileManagerDBCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val itemDao = database.itemDao()

                    // Folder with 10.000 children
                    val item10000 =
                        Item(0, "10.000", Constants.TYPE_FOLDER, "Example 10.000", "", PARENT_ID_MAIN)
                    itemDao.insertItem(item10000)
                    for (i in 0..5000) {
                        val id = UUID.randomUUID().toString()
                        val item =
                            Item(0, id, Constants.TYPE_FOLDER, "Example " + i, "", item10000.itemID)
                        itemDao.insertItem(item)
                    }
                    for (i in 0..5000) {
                        val id = UUID.randomUUID().toString()
                        val itemImage =
                            Item(0, id, Constants.TYPE_IMAGE,"Example " + i, BASE_URL, item10000.itemID)
                        itemDao.insertItem(itemImage)
                    }

                    // Folder with 2.000 pictures
                    val item2000 =
                        Item(0, "2.000", Constants.TYPE_FOLDER, "Example 2.000", "", PARENT_ID_MAIN)
                    itemDao.insertItem(item2000)
                    for (i in 0..2000) {
                        val id = UUID.randomUUID().toString()
                        val itemImage =
                            Item(0, id, Constants.TYPE_IMAGE,"Example " + i, BASE_URL, item2000.itemID)
                        itemDao.insertItem(itemImage)
                    }

                    // 20 nested folders
                    val item0 =
                        Item(0, UUID.randomUUID().toString(), Constants.TYPE_FOLDER, "Nested 1", "", PARENT_ID_MAIN)
                    itemDao.insertItem(item0)
                    val item1 =
                        Item(0, UUID.randomUUID().toString(), Constants.TYPE_FOLDER, "Nested 2", "", item0.itemID)
                    itemDao.insertItem(item1)
                    val item2 =
                        Item(0, UUID.randomUUID().toString(), Constants.TYPE_FOLDER, "Nested 3", "", item1.itemID)
                    itemDao.insertItem(item2)
                    val item3 =
                        Item(0, UUID.randomUUID().toString(), Constants.TYPE_FOLDER, "Nested 4","", item2.itemID)
                    itemDao.insertItem(item3)
                    val item4 =
                        Item(0, UUID.randomUUID().toString(), Constants.TYPE_FOLDER, "Nested 5", "", item3.itemID)
                    itemDao.insertItem(item4)
                    val item5 =
                        Item(0, UUID.randomUUID().toString(), Constants.TYPE_FOLDER, "Nested 6", "", item4.itemID)
                    itemDao.insertItem(item5)
                    val item6 =
                        Item(0, UUID.randomUUID().toString(), Constants.TYPE_FOLDER, "Nested 7", "", item5.itemID)
                    itemDao.insertItem(item6)
                    val item7 =
                        Item(0, UUID.randomUUID().toString(), Constants.TYPE_FOLDER, "Nested 8", "", item6.itemID)
                    itemDao.insertItem(item7)
                    val item8 =
                        Item(0, UUID.randomUUID().toString(), Constants.TYPE_FOLDER, "Nested 9", "", item7.itemID)
                    itemDao.insertItem(item8)
                    val item9 =
                        Item(0, UUID.randomUUID().toString(), Constants.TYPE_FOLDER, "Nested 10", "", item8.itemID)
                    itemDao.insertItem(item9)
                    val item10 =
                        Item(0, UUID.randomUUID().toString(), Constants.TYPE_FOLDER, "Nested 11", "", item9.itemID)
                    itemDao.insertItem(item10)
                    val item11 =
                        Item(0, UUID.randomUUID().toString(), Constants.TYPE_FOLDER, "Nested 12", "", item10.itemID)
                    itemDao.insertItem(item11)
                    val item12 =
                        Item(0, UUID.randomUUID().toString(), Constants.TYPE_FOLDER, "Nested 13", "", item11.itemID)
                    itemDao.insertItem(item12)
                    val item13 =
                        Item(0, UUID.randomUUID().toString(), Constants.TYPE_FOLDER, "Nested 14", "", item12.itemID)
                    itemDao.insertItem(item13)
                    val item14 =
                        Item(0, UUID.randomUUID().toString(), Constants.TYPE_FOLDER, "Nested 15", "", item13.itemID)
                    itemDao.insertItem(item14)
                    val item15 =
                        Item(0, UUID.randomUUID().toString(), Constants.TYPE_FOLDER, "Nested 16", "", item14.itemID)
                    itemDao.insertItem(item15)
                    val item16 =
                        Item(0, UUID.randomUUID().toString(), Constants.TYPE_FOLDER, "Nested 17", "", item15.itemID)
                    itemDao.insertItem(item16)
                    val item17 =
                        Item(0, UUID.randomUUID().toString(), Constants.TYPE_FOLDER, "Nested 18", "", item16.itemID)
                    itemDao.insertItem(item17)
                    val item18 =
                        Item(0, UUID.randomUUID().toString(), Constants.TYPE_FOLDER, "Nested 19", "", item17.itemID)
                    itemDao.insertItem(item18)
                    val item19 =
                        Item(0, UUID.randomUUID().toString(), Constants.TYPE_FOLDER, "Nested 20", "", item18.itemID)
                    itemDao.insertItem(item19)

                    // Empty folders
                    for (i in 0..5) {
                        val id = UUID.randomUUID().toString()
                        val item =
                            Item(0, id, Constants.TYPE_FOLDER, "Example " + i, "", PARENT_ID_MAIN)
                        itemDao.insertItem(item)
                    }
                }
            }
        }
    }
}