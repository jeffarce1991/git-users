package com.jeff.gitusers.database.usecase.local.loader

import com.jeff.gitusers.database.local.Photo
import com.jeff.gitusers.database.local.User
import com.jeff.gitusers.database.local.UserDetails
import com.jeff.gitusers.database.room.dao.PhotoDao
import com.jeff.gitusers.database.room.dao.UserDao
import com.jeff.gitusers.database.room.dao.UserDetailsDao
import io.reactivex.Single
import javax.inject.Inject

class DefaultUserDetailsLocalLoader @Inject
constructor(private val dao: UserDetailsDao): UserDetailsLocalLoader {

    override fun loadById(id: Int): Single<UserDetails> {
        return Single.fromCallable { dao.loadById(id) }
    }

}