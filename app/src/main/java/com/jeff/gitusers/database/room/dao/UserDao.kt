package com.jeff.gitusers.database.room.dao

import androidx.room.*
import com.jeff.gitusers.database.local.Photo
import com.jeff.gitusers.database.local.User

@Dao
interface UserDao {
    @Query("Select * FROM " + User.TABLE_NAME)
    fun loadAll(): List<User>

    /*@Query("Select * FROM " + User.TABLE_NAME +
            " WHERE "+ User.COLUMN_ID +" IN (:id)")
    fun loadAllByIds(id: IntArray): List<User>

    @Query("SELECT * FROM " + User.TABLE_NAME +
            " WHERE title LIKE :title AND title LIMIT 1")
    fun findByTitle(title: String): User*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Delete
    fun delete(user: User)

}