package com.filemanager.repository.db.item

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.filemanager.repository.db.FileManagerDB
import com.filemanager.utils.Constants
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*

@RunWith(AndroidJUnit4::class)
class ItemDaoTest {

    private var db: FileManagerDB? = null
    private var itemDao: ItemDao? = null

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FileManagerDB::class.java
        ).allowMainThreadQueries().build()
        itemDao = db!!.itemDao()
    }

    @After
    fun teardown() {
        db?.close()
    }

    @Test
    fun insert() {
        try {
            val item =
                Item(
                    0,
                    UUID.randomUUID().toString(),
                    Constants.TYPE_FOLDER,
                    "Test",
                    "",
                    "parent_id_main"
                )
            itemDao?.insertItem(item)
            val i: Item = itemDao!!.getItemById(item.itemID)
            assertEquals(i.itemID, item.itemID)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }
}