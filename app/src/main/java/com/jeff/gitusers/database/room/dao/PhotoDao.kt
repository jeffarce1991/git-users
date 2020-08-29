package com.jeff.template.database.room.dao

import androidx.room.*
import com.jeff.template.database.local.Photo

@Dao
interface PhotoDao {
    @Query("Select * FROM " + Photo.TABLE_NAME)
    fun loadAll(): List<Photo>

    @Query("Select * FROM " + Photo.TABLE_NAME +
            " WHERE "+ Photo.COLUMN_ID +" IN (:id)")
    fun loadAllByIds(id: IntArray): List<Photo>

    @Query("SELECT * FROM " + Photo.TABLE_NAME +
            " WHERE title LIKE :title AND title LIMIT 1")
    fun findByTitle(title: String): Photo

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(photos: List<Photo>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(photo: Photo)

    @Delete
    fun delete(photo: Photo)

}