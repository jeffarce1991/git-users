package com.jeff.gitusers.database.room.dao

import androidx.room.*
import com.jeff.gitusers.database.local.Notes
import com.jeff.gitusers.database.local.Photo
import com.jeff.gitusers.database.local.User
import com.jeff.gitusers.database.local.UserDetails

@Dao
interface NotesDao {

    @Query("Select * FROM " + Notes.TABLE_NAME)
    fun loadAll(): List<Notes>

    @Query("SELECT * FROM " + Notes.TABLE_NAME +
            " WHERE " + Notes.COLUMN_ID +" LIKE :id")
    fun loadById(id: Int): Notes

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(notes: Notes)

}