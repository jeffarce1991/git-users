package com.jeff.gitusers.database.usecase.local.loader

import com.jeff.gitusers.database.local.User
import com.jeff.gitusers.database.local.UserDetails
import io.reactivex.Single

interface UserDetailsLocalLoader {
    fun loadById(id: Int): Single<UserDetails>
}