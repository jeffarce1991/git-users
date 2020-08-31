package com.jeff.gitusers.database.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = Notes.TABLE_NAME)
data class Notes constructor(
                 @ColumnInfo(name = "id")
                 @PrimaryKey var id: Int,
                 @ColumnInfo(name = "content")
                 var content: String
                ) {

    constructor() : this(-1, "")

    companion object {

        const val COLUMN_ID = "id"
        const val TABLE_NAME = "notes"
    }
}
