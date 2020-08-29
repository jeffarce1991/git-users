package com.jeff.gitusers.database.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = Photo.TABLE_NAME)
data class User constructor(
                 @ColumnInfo(name = "id")
                 @PrimaryKey(autoGenerate = true) var id: Int,
                 @ColumnInfo(name = "login")
                 var login: String,
                 @ColumnInfo(name = "avatar_url")
                 var avatarUrl: String,
                 @ColumnInfo(name = "url")
                 var url: String
                ) {

    constructor() : this(-1, "", "", "")

    companion object {

        const val COLUMN_ID = "id"
        const val TABLE_NAME = "users"
    }
}
