package com.jeff.gitusers.database.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Photo.TABLE_NAME)
data class Photo constructor(
                 @ColumnInfo(name = "id")
                 @PrimaryKey(autoGenerate = true) var id: Int,
                 @ColumnInfo(name = "album_id")
                 var albumId: Int,
                 @ColumnInfo(name = "title")
                 var title: String,
                 @ColumnInfo(name = "url")
                 var url: String,
                 @ColumnInfo(name = "thumbnail_url")
                 var thumbnailUrl: String) {

    companion object {

        const val COLUMN_DEAL_ID = "photo_id"
        const val COLUMN_ID = "id"
        const val TABLE_NAME = "photos"
    }
}
