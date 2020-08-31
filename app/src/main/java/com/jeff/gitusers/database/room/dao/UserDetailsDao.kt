package com.jeff.gitusers.database.room.dao

import androidx.room.*
import com.jeff.gitusers.database.local.UserDetails

@Dao
interface UserDetailsDao {
    @Query("Select * FROM " + UserDetails.TABLE_NAME)
    fun loadAll(): List<UserDetails>

    @Query("SELECT * FROM " + UserDetails.TABLE_NAME +
            " WHERE " + UserDetails.COLUMN_ID +" LIKE :id")
    fun loadById(id: Int): UserDetails

    /*@Query("Select * FROM " + User.TABLE_NAME +
            " WHERE "+ User.COLUMN_ID +" IN (:id)")
    fun loadAllByIds(id: IntArray): List<User>

    @Query("SELECT * FROM " + User.TABLE_NAME +
            " WHERE title LIKE :title AND title LIMIT 1")
    fun findByTitle(title: String): User*/

    /*@Query("UPDATE ${UserDetails.TABLE_NAME} SET notes = :newNotes WHERE id =:id")
    fun updateNotes(newNotes: String?, id: Int)*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: List<UserDetails>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserDetails)

    @Delete
    fun delete(user: UserDetails)

}