package com.filemanager

import android.app.Application
import com.filemanager.repository.db.FileManagerDB
import com.filemanager.repository.ItemRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class FileManagerApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())
    private val database by lazy { FileManagerDB.getDatabase(this, applicationScope) }
    val repository by lazy {
        ItemRepository(
            database.itemDao()
        )
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: FileManagerApplication
            private set
    }
}