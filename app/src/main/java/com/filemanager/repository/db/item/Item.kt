package com.filemanager.repository.db.item

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
class Item(
    @PrimaryKey(autoGenerate = true) var id: Long,
    @ColumnInfo(name = "itemID") var itemID: String,
    @ColumnInfo(name = "type") var type: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "path") var path: String,
    @ColumnInfo(name = "parentID") var parentID: String
) {
    constructor(
        itemID: String,
        type: String,
        name: String,
        path: String,
        parentID: String
    )
            : this(0, itemID, type, name, path, parentID) {
        this.itemID = itemID
        this.type = type
        this.name = name
        this.path = path
        this.parentID = parentID
    }
}