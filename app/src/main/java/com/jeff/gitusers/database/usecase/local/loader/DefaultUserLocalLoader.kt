package com.jeff.gitusers.database.usecase.local.loader

import com.jeff.gitusers.database.local.Photo
import com.jeff.gitusers.database.local.User
import com.jeff.gitusers.database.room.dao.PhotoDao
import com.jeff.gitusers.database.room.dao.UserDao
import io.reactivex.Single
import javax.inject.Inject

class DefaultUserLocalLoader @Inject
constructor(private val dao: UserDao): UserLocalLoader {

    override fun loadAll(): Single<List<User>> {
        return Single.fromCallable { dao.loadAll() }
    }

}