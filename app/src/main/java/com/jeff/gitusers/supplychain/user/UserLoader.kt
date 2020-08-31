package com.jeff.gitusers.supplychain.user

import com.jeff.gitusers.database.local.User
import com.jeff.gitusers.database.local.UserDetails
import io.reactivex.Single

interface UserLoader {

    fun loadUsers(): Single<List<User>>
    fun loadMoreUsers(id: Int): Single<List<User>>

    fun loadUsersLocally(): Single<List<User>>
    fun loadInitialUsersRemotely(): Single<List<User>>

    fun loadUserDetailsRemotely(login: String): Single<UserDetails>
}