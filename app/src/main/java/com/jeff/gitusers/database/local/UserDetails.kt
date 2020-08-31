package com.jeff.gitusers.database.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = UserDetails.TABLE_NAME)
data class UserDetails constructor(
                 @ColumnInfo(name = "id")
                 @PrimaryKey var id: Int,
                 @ColumnInfo(name = "login")
                 var login: String,
                 @ColumnInfo(name = "name")
                 var name: String? = "",
                 @ColumnInfo(name = "company")
                 var company: String? = null,
                 @ColumnInfo(name = "blog")
                 var blog: String? = null,
                 @ColumnInfo(name = "location")
                 var location: String? = null,
                 @ColumnInfo(name = "email")
                 var email: String? = null,
                 @ColumnInfo(name = "bio")
                 var bio: String? = null,
                 @ColumnInfo(name = "twitter_username")
                 var twitterUsername: String? = null,
                 @ColumnInfo(name = "public_repos")
                 var public_repos: Int? = null,
                 @ColumnInfo(name = "public_gists")
                 var public_gists: Int? = null,
                 @ColumnInfo(name = "followers")
                 var followers: Int? = null,
                 @ColumnInfo(name = "following")
                 var following: Int? = null,
                 @ColumnInfo(name = "created_at")
                 var created_at: String? = null,
                 @ColumnInfo(name = "updated_at")
                 var updated_at: String? = null
                ) {

    constructor() : this(-1, "", "", "", "", "", "", "", "", -1, -1, -1, -1, "", "")

    companion object {

        const val COLUMN_ID = "id"
        const val TABLE_NAME = "user_details"
    }
}
