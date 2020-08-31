package com.jeff.gitusers.database.usecase.local.loader

import com.jeff.gitusers.database.local.User
import io.reactivex.Single

interface UserLocalLoader {
    fun loadAll(): Single<List<User>>
}