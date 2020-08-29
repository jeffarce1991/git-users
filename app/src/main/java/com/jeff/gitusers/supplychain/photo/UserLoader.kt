package com.jeff.gitusers.supplychain.photo

import com.jeff.gitusers.database.local.Photo
import com.jeff.gitusers.database.local.User
import io.reactivex.Single

interface UserLoader {

    fun loadInitialUsers(): Single<List<User>>
    fun loadMoreUsers(id: Int): Single<List<User>>

    fun loadInitialUsersLocally(): Single<List<User>>
}